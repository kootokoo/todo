var donetodo = {
    init : function () {
        var _this = this;
        $('.btn_done').on('click', function (event) {
            _this.done(event);
        });
    },
    done : function (event) {
        const doneButton = $(event.currentTarget);
        const id = doneButton.data("id");
        $.ajax({
            type: 'DELETE',
            url: '/api/todo/done/' + id
        }).done(function() {
            alert('할일이 done 되었습니다');
            location.reload();
        }).fail(function (data) {
            alert(data.responseJSON.message);
        });
    }

};

donetodo.init();
