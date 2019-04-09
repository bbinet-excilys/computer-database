var introduced;
var discontinued;

$("#addComputerForm").validate({
  rules: {
    "computerName": {
      required: true,
      regex: /^[\w\d]+[\w\d\ \-\_\.]*/
    },
    "computerIntroduced": {
      required: {
        depends: function (element) {
          if (typeof discontinued === 'undefined') {
            return false;
          } else {
            return !isNaN(discontinued.valueOf());
          }
        }
      }
    }
  },
  messages: {
    "computerName": {
      required: "A name must be entered for the computer",
      regex: "The name can only contain letters, numbers, spaces, dashes and undescores"
    },
    "computerIntroduced": {
      required: "Set an introduction date before the the discontinuation date or unset the discontinuation date"
    }
  },
  errorClass: "uk-form-danger"
});

$("#computerName").focusout(function (e) {
  $(this).val($(this).val().trim());
});

$("#computerIntroduced").change(function () {
  introduced = new Date(this.value);
  if (!isNaN(introduced.valueOf())) {
    var tempDate = new Date(this.value);
    tempDate.setDate(tempDate.getDate() + 1);
    var isoDate = tempDate.toISOString();
    var limDate = isoDate.substr(0, isoDate.indexOf('T'));
    $("#computerDiscontinued")[0].setAttribute("min", limDate);
    $("#computerDiscontinued").rules("add", {
      min: {
        param: introduced.toISOString(),
        depends: function (element) {
          if (typeof introduced === 'undefined') {
            return false;
          } else {
            return !isNaN(introduced.valueOf());
          }
        }
      }
    });
  } else {
    $("#computerDiscontinued")[0].removeAttribute("min");
    $("#computerDiscontinued").rules("remove", "min");
  }
});

$("#computerDiscontinued").change(function () {
  discontinued = new Date(this.value);
  console.log("undefined :" + isNaN(discontinued.valueOf()))
});

$("#computerCompanyId").change(function () {
  $("#computerCompanyName").val($("#computerCompanyId option:selected").text());
  console.log("Changed : " + $("#computerCompanyId option:selected").text());
});