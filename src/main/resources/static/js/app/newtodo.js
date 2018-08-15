var newtodo = {
    init : function () {
        var _this = this;
        $('#btn_save').on('click', function () {
            _this.save();
        });
    },
    save : function () {
        var data = {
            desc: $('#desc').val(),
            links: $('#links').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/todo',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('할일이 등록되었습니다.');
            location.reload();
        }).fail(function (data) {
            alert(data.responseJSON.message);
        });
    }

};

newtodo.init();