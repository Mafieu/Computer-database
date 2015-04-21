// On load, let's check values and update css accordingly
$(function() {
	if ($("#computerName").val().trim() == "") {
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
	var date = new Date(Date.parse(element.val()))
	var wrongDate = isNaN(date) || date.getFullYear() < 1970 || !(date.getFullYear() <= 2038 && date.getMonth() <= 0 && date.getDate() <= 19)
	if(!wrongDate){
		if((month=date.getMonth()+1) < 10)
			month = "0" + (date.getMonth()+1)
		if((day=date.getDate()) < 10)
			day = "0" + date.getDate()
		element.val(date.getFullYear() + "-" + month + "-" + day)
	}
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
	if ($("#computerName").val().trim() == "") {
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

	if ($("#computerName").val().trim() == "") {
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