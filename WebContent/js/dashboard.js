//On load
$(function () {
  // Default: hide edit mode
  $(".editMode").hide();

  // Click on "selectall" box
  $("#selectall").click(function () {
    $('.uk-checkbox').prop('checked', this.checked);
  });

  // Click on a checkbox
  $(".uk-checkbox").click(function () {
    if ($(".uk-checkbox").length == $(".uk-checkbox:checked").length) {
      $("#selectall").prop("checked", true);
    } else {
      $("#selectall").prop("checked", false);
    }
    if ($(".uk-checkbox:checked").length != 0) {
      $("#deleteSelected").enable();
    } else {
      $("#deleteSelected").disable();
    }
  });

});


// Function setCheckboxValues
(function ($) {

  $.fn.setCheckboxValues = function (formFieldName, checkboxFieldName) {

    var str = $('.' + checkboxFieldName + ':checked').map(function () {
      return this.value;
    }).get().join();

    $(this).attr('value', str);

    return this;
  };

}(jQuery));

// Function toggleEditMode
(function ($) {

  $.fn.toggleEditMode = function () {
    if ($(".editMode").is(":visible")) {
      $(".editMode").hide();
      $("#editComputer").text("Edit");
    } else {
      $(".editMode").show();
      $("#editComputer").text("View");
    }
    return this;
  };

}(jQuery));


// Function delete selected: Asks for confirmation to delete selected computers, then submits it to the deleteForm
(function ($) {
  $.fn.deleteSelected = function () {
    if (confirm("Are you sure you want to delete the selected computers?")) {
      $('#deleteForm input[name=selection]').setCheckboxValues('selection', 'uk-checkbox');
      $('#deleteForm').submit();
    }
  };
}(jQuery));



//Event handling
//Onkeydown
$(document).keydown(function (e) {

  switch (e.keyCode) {
    //DEL key
    case 46:
      if ($(".editMode").is(":visible") && $(".uk-checkbox:checked").length != 0) {
        $.fn.deleteSelected();
      }
      break;
      //E key (CTRL+E will switch to edit mode)
    case 69:
      if (e.ctrlKey) {
        $.fn.toggleEditMode();
      }
      break;
  }
});

function setNumberPerPage(number) {
  number = number || 10;
  $("#hiddenForm input[name=pageSize]").val(number);
}