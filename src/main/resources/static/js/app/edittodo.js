var main = {
    init : function () {
        var _this = this;
        $('#btn-edit').on('click', function () {
            _this.edit();
        });
    },
    edit : function () {
        var data = {
            id : $('#edit_id').val(),
            desc: $('#edit_desc').val(),
            links: $('#edit_links').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/edit',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('할일이 수정되었습니다.');
            location.reload();
        }).fail(function (data) {
            alert(data.responseJSON.message);
        });
    }
};

main.init();

