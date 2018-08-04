var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
    },
    save : function () {
        var data = {
            description: $('#description').val(),
            linkIds :$('#linkIds').val()
        };
        $.ajax({
            type: 'POST',
            url: '/api/add',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            location.reload();
            alert('할일이 등록되었습니다.');
        }).fail(function (error) {
            alert(error);
        });
    }

};

main.init();