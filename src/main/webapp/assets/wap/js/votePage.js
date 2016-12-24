$(document).ready(function() {
    $('#scoreBtn').on('click', function() {
        var voteId = $('#temp').attr('vid');
        var competitorId = $('#temp').attr('cid');
        var score = $('#scoreIpt').val();

        var phone = $('#phoneIpt').val();

        if(!phone || !(/^1\d{10}$/.test(phone)) ) {
            $('#phone-error-dialog').removeClass('aui-hidden');
            return;
        }

        if(!score || !/^\d+(\.\d+)?$/.test(score)) {
            $('#score-error-dialog').removeClass('aui-hidden');
            return;
        }

        var url = '/wap/votes/voting';

        $.post(url, {
            voteId: voteId,
            competitorId: competitorId,
            score: score,
            voteType: 'SCORE',
            phone: phone
        }, function(result) {
            if(result.success) {
                $("#success").attr('style', 'display:black');
                setTimeout(function(){
                    $("#success").attr('style', 'display:none');
                    location.href = '/wap/votes/vote/' + voteId + '/competitor/' + competitorId;
                },2000)
            }else {
                $('#result-error-dialog').removeClass('aui-hidden');
            }
        }, 'json');
    });

    $('#zanchengBtn').on('click', function() {
        var voteId = $('#temp').attr('vid');
        var competitorId = $('#temp').attr('cid');

        var phone = $('#phoneIpt').val();

        if(!phone || !/^1\d{10}$/.test(phone)) {
            $('#phone-error-dialog').removeClass('aui-hidden');
            return;
        }

        var url = '/wap/votes/voting';

        $.post(url, {
            voteId: voteId,
            competitorId: competitorId,
            voteType: 'CHECK',
            addIn: true,
            addOut: false,
            phone: phone
        }, function(result) {
            if(result.success) {
                $("#success").attr('style', 'display:black');
                setTimeout(function(){
                    $("#success").attr('style', 'display:none');
                    location.href = '/wap/votes/vote/' + voteId + '/competitor/' + competitorId;
                },2000)
            }else {
                $('#result-error-dialog').removeClass('aui-hidden');
            }
        }, 'json');
    });

    $('#fanduiBtn').on('click', function() {
        var voteId = $('#temp').attr('vid');
        var competitorId = $('#temp').attr('cid');

        var phone = $('#phoneIpt').val();

        if(!phone || !/^1\d{10}$/.test(phone)) {
            $('#phone-error-dialog').removeClass('aui-hidden');
            return;
        }

        var url = '/wap/votes/voting';

        $.post(url, {
            voteId: voteId,
            competitorId: competitorId,
            voteType: 'CHECK',
            addOut: true,
            addIn: false,
            phone: phone
        }, function(result) {
            if(result.success) {
                $("#success").attr('style', 'display:black');
                setTimeout(function(){
                    $("#success").attr('style', 'display:none');
                    location.href = '/wap/votes/vote/' + voteId + '/competitor/' + competitorId;
                },2000)
            }else {
                $('#result-error-dialog').removeClass('aui-hidden');
            }
        }, 'json');
    });

    $('#voteBtn').on('click', function() {
        var voteId = $('#temp').attr('vid');
        var competitorId = $('#temp').attr('cid');

        var phone = $('#phoneIpt').val();

        if(!phone || !/^1\d{10}$/.test(phone)) {
            $('#phone-error-dialog').removeClass('aui-hidden');
            return;
        }

        var url = '/wap/votes/voting';

        $.post(url, {
            voteId: voteId,
            competitorId: competitorId,
            voteType: 'VOTE',
            phone: phone
        }, function(result) {
            if(result.success) {
                $("#success").attr('style', 'display:black');
                setTimeout(function(){
                    $("#success").attr('style', 'display:none');
                    location.href = '/wap/votes/vote/' + voteId + '/competitor/' + competitorId;
                },2000)
            }else {
                $('#result-error-dialog').removeClass('aui-hidden');
            }
        }, 'json');
    });

    $('.confirmbtn').on('click', function() {
        $('#phone-error-dialog').addClass('aui-hidden');
    });
    $('.confirmbtn-score').on('click', function() {
        $('#score-error-dialog').addClass('aui-hidden');
    });
    $('.confirmbtn-result').on('click', function() {
        $('#result-error-dialog').addClass('aui-hidden');
    });

});