$(document).ready(function() {
    $('#scoreBtn').on('click', function() {
        var voteId = $('#temp').attr('vid');
        var competitorId = $('#temp').attr('cid');
        var score = $('#scoreIpt').val();

        var phone = $('#nameIpt').val();

        if(!phone || !/^1\d{10}$/.test(phone) ) {
            alert('请填写正确的电话号码');
            return;
        }

        if(!score || !/^\d+(\.\d+)?$/.test(score)) {
            alert('请填写正确的分数');
            return;
        }

        var url = '/wap/votes/addScore/' + voteId + '/' + competitorId + '/' + score + '/' + phone;

        $.post(url, null, function(result) {
            alert(result.msg);
            location.href = '/wap/votes/vote/' + voteId + '/competitor/' + competitorId;
        }, 'json');
    });

    $('#zanchengBtn').on('click', function() {
        var voteId = $('#temp').attr('vid');
        var competitorId = $('#temp').attr('cid');

        var phone = $('#nameIpt').val();

        if(!phone || !/^1\d{10}$/.test(phone)) {
            alert('请填写正确的电话号码');
            return;
        }

        var url = '/wap/votes/addIn/' + voteId + '/' + competitorId + '/' + phone;

        $.post(url, null, function(result) {
            alert(result.msg);
            location.href = '/wap/votes/vote/' + voteId + '/competitor/' + competitorId;
        }, 'json');
    });

    $('#fanduiBtn').on('click', function() {
        var voteId = $('#temp').attr('vid');
        var competitorId = $('#temp').attr('cid');

        var phone = $('#nameIpt').val();

        if(!phone || !/^1\d{10}$/.test(phone)) {
            alert('请填写正确的电话号码');
            return;
        }

        var url = '/wap/votes/addOut/' + voteId + '/' + competitorId + '/' + phone;

        $.post(url, null, function(result) {
            alert(result.msg);
            location.href = '/wap/votes/vote/' + voteId + '/competitor/' + competitorId;
        }, 'json');
    });

    $('#voteBtn').on('click', function() {
        var voteId = $('#temp').attr('vid');
        var competitorId = $('#temp').attr('cid');

        var phone = $('#nameIpt').val();

        if(!phone || !/^1\d{10}$/.test(phone)) {
            alert('请填写正确的电话号码');
            return;
        }

        var url = '/wap/votes/addVote/' + voteId + '/' + competitorId + '/' + phone;

        $.post(url, null, function(result) {
            alert(result.msg);
            location.href = '/wap/votes/vote/' + voteId + '/competitor/' + competitorId;
        }, 'json');
    });

});