// On load, let's check values and update css accordingly
$(function() {
	if ($("#name").val().trim() == "") {
		toggleError($("#computerNameDiv"))
	} else {
		toggleSuccess($("#computerNameDiv"))
	}
	if ($("#introduced").val() != "" && checkDateFormat($("#introduced"))) {
		toggleError($("#introducedDiv"))
	} else {
		toggleSuccess($("#introducedDiv"))
	}
	if ($("#discontinued").val() != ""
			&& checkDateFormat($("#discontinued"))) {
		toggleError($("#discontinuedDiv"))
	} else {
		toggleSuccess($("#discontinuedDiv"))
	}
})

// Functions to chack date format and change the css
var checkDateFormat = function(element) {
	var format
	switch($.cookie("language")){
		case "fr":
			format = "DD-MM-YYYY"
			break
		default:
			format = "MM-DD-YYYY"
			break
	}
	var date = moment(element.val(), format, true)
	var wrongDate = !date.isValid() || date.year() < 1970 || date.year() >= 2038
	return wrongDate
}
var toggleError = function(element) {
	element.toggleClass("has-error", true)
	element.toggleClass("has-success", false)
}
var toggleSuccess = function(element) {
	element.toggleClass("has-error", false)
	element.toggleClass("has-success", true)
}

// Events for change in css in real time
$("#computerNameDiv").keyup(function(e) {
	if ($("#name").val().trim() == "") {
		toggleError($("#computerNameDiv"))
	} else {
		toggleSuccess($("#computerNameDiv"))
	}
})
$("#introducedDiv").keyup(
		function(e) {
			if ($("#introduced").val() != ""
					&& checkDateFormat($("#introduced"))) {
				toggleError($("#introducedDiv"))
			} else {
				toggleSuccess($("#introducedDiv"))
			}
		})
$("#discontinuedDiv").keyup(
		function(e) {
			if ($("#discontinued").val() != ""
					&& checkDateFormat($("#discontinued"))) {
				toggleError($("#discontinuedDiv"))
			} else {
				toggleSuccess($("#discontinuedDiv"))
			}
		})

// Function that stops the form accordingly
var checkValues = function() {
	var bool = true

	if ($("#name").val().trim() == "") {
		bool = false
	}
	if ($("#introduced").val() != "" && checkDateFormat($("#introduced"))) {
		bool = false
	}
	if ($("#discontinued").val() != ""
			&& checkDateFormat($("#discontinued").val())) {
		bool = false
	}
	return bool
}