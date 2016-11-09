$(document).ready(function() {
    $('#scoreBtn').on('click', function() {
        var voteId = $('#temp').attr('vid');
        var competitorId = $('#temp').attr('cid');
        var score = $('#scoreIpt').val();

        var url = '/wap/votes/addScore/' + voteId + '/' + competitorId + '/' + score;

        $.post(url, null, function(result) {
            alert(result.msg);
            location.href = '/wap/votes/vote/' + voteId + '/competitor/' + competitorId;
        }, 'json');
    });

    $('#zanchengBtn').on('click', function() {
        var voteId = $('#temp').attr('vid');
        var competitorId = $('#temp').attr('cid');

        var url = '/wap/votes/addIn/' + voteId + '/' + competitorId;

        $.post(url, null, function(result) {
            alert(result.msg);
            location.href = '/wap/votes/vote/' + voteId + '/competitor/' + competitorId;
        }, 'json');
    });

    $('#fanduiBtn').on('click', function() {
        var voteId = $('#temp').attr('vid');
        var competitorId = $('#temp').attr('cid');

        var url = '/wap/votes/addOut/' + voteId + '/' + competitorId;

        $.post(url, null, function(result) {
            alert(result.msg);
            location.href = '/wap/votes/vote/' + voteId + '/competitor/' + competitorId;
        }, 'json');
    });

    $('#voteBtn').on('click', function() {
        var voteId = $('#temp').attr('vid');
        var competitorId = $('#temp').attr('cid');

        var url = '/wap/votes/addVote/' + voteId + '/' + competitorId;;

        $.post(url, null, function(result) {
            alert(result.msg);
            location.href = '/wap/votes/vote/' + voteId + '/competitor/' + competitorId;
        }, 'json');
    });

});