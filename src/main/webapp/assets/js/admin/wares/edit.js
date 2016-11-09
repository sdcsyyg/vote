$(document).ready(function() {

    $('.chzn-select').chosen({
        allow_single_deselect: true
    });

    $('#tags-labels').tagsInput({
        'defaultText': '添加',
        'height': '50px',
        'width': '500px'
    });

    KindEditor.ready(function(K) {

        // 富文本编辑器
        var editor = K.create('#editor-content', {
            minHeight: 300,
            urlType : 'domain',
            uploadJson: '/admin/mfiles/upload',
            autoHeightMode : true,
            afterCreate : function() {
                this.loadPlugin('autoheight');
            }
        });

        $('#model-form-save').on('click', function() {
            var $form = $('#model-form');
    
            var name = $form.find('input[name="name"]').val();
            if(!name || name === '') {
                alert('名称不能为空');
                return false;
            }

            $('#editor-input').val(editor.html());
    
            $.post($form.attr('action'), $form.serialize(), function(result) {
                if(result.success) {
                    location.href = '/admin/wares/list';
                }else {
                    alert(result.msg);
                }
            }, 'json');
        });
    });
});