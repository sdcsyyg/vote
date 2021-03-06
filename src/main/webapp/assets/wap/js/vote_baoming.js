$(document).ready(function() {
    $('#model-form-save').on('click', function() {

        var $form = $('#model-form');
        var vid = $form.attr("vid");
        var realname = $form.find('input[name="name"]').val();
        if(realname == '') {
            alert('姓名不能为空');
            $form.find('input[name="name"]').focus();
            return false;
        }

        var phone = $form.find('input[name="phone"]').val();
        if(!/^1\d{10}$/.test(phone)) {
            alert('请输入正确的手机号码');
            $form.find('input[name="phone"]').focus();
            return false;
        }

        $.post($form.attr('action'), $form.serialize(), function(result) {
            if(result.success) {
                $("#success").attr('style', 'display:black');
                setTimeout(function(){
                    $("#success").attr('style', 'display:none');
                    location.href = '/wap/votes/vote/' + vid;
                },2000)
            } else {
                $("#error").attr('style', 'display:black');
                setTimeout(function(){
                    $("#error").attr('style', 'display:none');
                },2000)
            }
        }, 'json');
    });

    $('#model-form-reset').on('click', function() {
        $('#model-form')[0].reset();
    });
});