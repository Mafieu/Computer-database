// On load, let's check values and update css accordingly
$(function(){
	if($("#computerName").val() == ""){
		toggleError($("#computerNameDiv"))
	} else {
		toggleSuccess($("#computerNameDiv"))
	}
	if($("#introduced").val() != "" && checkDateFormat($("#introduced").val())){
		toggleError($("#introducedDiv"))
	} else {
		toggleSuccess($("#introducedDiv"))
	}
	if($("#discontinued").val() != "" && checkDateFormat($("#discontinued").val())){
		toggleError($("#discontinuedDiv"))
	} else {
		toggleSuccess($("#discontinuedDiv"))
	}
})

// Functions to chack date format and change the css
var checkDateFormat = function(string){
	return string.match(/[1-2][0-9]{3}-[0-9]?[0-9]-[0-9]?[0-9]/g) == null
}
var toggleError = function(element){
	element.toggleClass("has-error", true)
	element.toggleClass("has-success", false)
}
var toggleSuccess = function(element){
	element.toggleClass("has-error", false)
	element.toggleClass("has-success", true)
}

// Events for change in css in real time
$("#computerNameDiv").keyup(function(e){
	if($("#computerName").val() == ""){
		toggleError($("#computerNameDiv"))
	} else {
		toggleSuccess($("#computerNameDiv"))
	}
})
$("#introducedDiv").keyup(function(e){
	if($("#introduced").val() != "" && checkDateFormat($("#introduced").val())){
		toggleError($("#introducedDiv"))
	} else {
		toggleSuccess($("#introducedDiv"))
	}
})
$("#discontinuedDiv").keyup(function(e){
	if($("#discontinued").val() != "" && checkDateFormat($("#discontinued").val())){
		toggleError($("#discontinuedDiv"))
	} else {
		toggleSuccess($("#discontinuedDiv"))
	}
})

// Function that stops the form accordingly
var checkValues = function(){
	var bool = true

	if($("#computerName").val() == ""){
		bool = false
	}
	if($("#introduced").val() != "" && checkDateFormat($("#introduced").val())){
		bool = false
	}
	if($("#discontinued").val() != "" && checkDateFormat($("#discontinued").val())){
		bool = false
	}
	return bool
}