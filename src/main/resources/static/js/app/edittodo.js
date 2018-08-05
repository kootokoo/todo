var main = {
    init : function () {
        var _this = this;
        $('#btn_edit').on('click', function () {
            _this.edit();
        });
        $('.bts_show_edit_modal').on('click', function () {
            _this.set_modal_data(event);
        });
    },
    set_modal_data: function() {
        const showModalButton = $(event.currentTarget);
        const id = showModalButton.data("id");
        const desc = showModalButton.data("desc");
        const links = showModalButton.data("links");


        $("#edit_id").val(id);
        $("#edit_desc").val(desc);
        $("#edit_links").val(links);
    }
    ,
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

