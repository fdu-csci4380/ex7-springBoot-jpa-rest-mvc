/**
 * 
 */
//$(function(){
// NOTE ilker above is shorthand for below line
$(document).ready(function(){
	// create new user or edit one
	$('.nBtn, .table .eBtn').on('click', function(event){
//		console.log("ILKER -->");	// NOTE ilker, this is JS way to 
//		debugger;	// NOTE ilker, this is JS way of putting breakpoint hard coded into your code.If you uncomment this, this JS code will stop at this line for you to continue in debugger of DevTool of browser

		// NOTE ilker if you have href="" in anchor tag this jQuery marker triggers on, then you need to have below line to be able to see modal popup
		//         or if you have th:href="@{detail/(studentId=${student.studentId})}", then you need below line to stop it from showing the result as json object
		event.preventDefault();	
		var href = $(this).attr('href');	// NOTE ilker will be like;   /mvc/student/detail/?studentId=110
//		console.log(href);   // TODO commentMe
		var text = $(this).text();
		if(text=='Edit') {
			// NOTE ilker, make ajax (REST) call via jQuery's get. Then once REST service returns data (in data4student variable) back, below function executes "asynchronously"
			$.get(href, function(data4student, status){
				$('.myForm #studentId').val(data4student.studentId);
				$('.myForm #name').val(data4student.name);
				$('.myForm #lastname').val(data4student.lastname);
				$('.myForm #grade').val(data4student.grade);
				$('.myForm #age').val(data4student.age);
				$('.myForm #isFullTime').val(data4student.isFullTime);
				$('.myForm #updatedOn').val(data4student.updatedOn);
			});
			// NOTE ilker, let Bootstrap modal popup via below jQuery call
			$('.myForm #exampleModal').modal();	// modal(), modal('show'), modal('toggle')
		} else {
			// NOTE ilker, hide studentId input div. And disable studentId input. Since studentId is "@GeneratedValue(strategy=GenerationType.AUTO)" in StudentEntity and inputs that can not be parsed to Integer will cause 400 Bad Request upon POST of form, no need for user to be able to enter anything here
			$('.myForm #studentId').prop("disabled", true); // example of setting an attribute of the element
			$('.myForm #studentId').prop("type", "hidden"); // NOTE this does not hide the label, just hides the input element, so below line is better
			$('.myForm #studentId').parent().hide();	// NOTE ilker using jQuery APIs parent() to get parent div of #studentId input and then hide() to hide that div
			// initialize the fields of modal
			$('.myForm #studentId').val('');	// NOTE ilker for post form submission to be auto serialized to StudentEntity at StudentController's method, this needs to be initialized to a value that can be parsed to Integer since studentId attribute is an Integer. Otherwise you will get 400 Bad Request upon POST submit
			$('.myForm #name').val('');
			$('.myForm #lastname').val('');
			$('.myForm #grade').val('');
			$('.myForm #age').val('');
			$('.myForm #isFullTime').val('');
			$('.myForm #updatedOn').val('');
			// let Bootstrap modal popup via below jQuery call
			$('.myForm #exampleModal').modal();	// modal(), modal('show'), modal('toggle')			
		}
	});
	
	$('.table .dBtn').on('click', function(event){
		event.preventDefault();	
		var href = $(this).attr('href');
		$('#exampleModal4delete #dConfBtn').attr('href', href);
		// let Bootstrap modal popup via below jQuery call
		$('#exampleModal4delete').modal();	// modal(), modal('show'), modal('toggle')			
		

	});
		
});