var main = {
    init : function () {
        var _this = this;
        $('.btn-done').on('click', function (event) {
            _this.done(event);
        });
    },
    done : function (event) {
        const doneButton = $(event.currentTarget);
        const id = doneButton.data("id");
        $.ajax({
            type: 'GET',
            url: '/api/done/' + id,
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('할일이 done 되었습니다');
            location.reload();
        }).fail(function (data) {
            alert(data.responseJSON.message);
        });
    }

};

main.init();
