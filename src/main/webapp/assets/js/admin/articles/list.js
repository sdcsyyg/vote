$(function () {

    var $table = $('#model-table-view');
    $table.bootstrapTable({
        method: 'get',
        url: '/admin/articles/paged/',
        cache: false,
        search: true,
        showColumns: true,
        minimumCountColumns : 2,
        showRefresh: true,
        showToggle: true,
        pagination: true,
        sidePagination: 'server',
        pageSize: 20,
        columns: [{
            field : 'cid',
            title: '分类',
            width: '80px'
        }, {
            field : 'labelItems',
            title: '标签',
            width: '200px',
            formatter: function(value, row) {
                if(value) {
                    var html = '';
                    for(var i in value) {
                        html += '<span class="label-cell" style="margin-right:5px;">' + value[i] + '</span>';
                    }
                    return html;
                }
            }
        }, {
            field : 'cover',
            title: '封面',
            width: '100px',
            formatter: function(value, row) {
                if(value) {
                    return '<img src="' + value + '" width="60px" height="40px"/>';
                }
                return '-';
            }
        }, {
            field : 'title',
            title: '标题',
            width: '300px',
            formatter: function(value, row) {
                if(row.link && row.link != '') {
                    return '<a target="_blank" href="' + row.link + '">' + value + '</a>';
                }
                return value;
            }
        }, {
            field : 'summary',
            title: '摘要',
            width: '300px',
            formatter: function(value, row) {
                return '<p class="nowrap-multi">' + value + '</p>';
            }
        }, {
            filed: 'operate',
            title: '操作',
            width: '170px',
            align: 'center',
            formatter : function(value, row) {
                return [
                    '<div class="operate-tool-group">',
                        '<span class="fa fa-pencil edit" title="编辑"></span>',
                        '<span class="fa fa-trash trash" title="删除"></span>',
                    '</div>'
                ].join('');
            },
            events : {
                'click .edit': function (e, value, row, index) {
                    location.href = "/admin/articles/edit/" + row.id;
                },
                'click .trash': function (e, value, row, index) {
                    if(confirm('您确定要删除该文章吗？') ){
                        $.ajax({
                            type: "GET",
                            dataType: "json",
                            url: '/admin/articles/delete/' + row.id,
                            success: function (result) {
                                alert(result.msg);
                                if(result.success) {
                                    location.href = "/admin/articles/list";
                                }
                            }
                        });
                    }
                }
            }
        }],
        responseHandler: function(res) {
            return {
                rows: res.content,
                total: res.totalElements
            }
        }
    });

});