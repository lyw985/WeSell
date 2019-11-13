
(function($) {
	$.fn.validationEngineLanguage = function() {
	};
	$.validationEngineLanguage = {
		newLang : function() {
			$.validationEngineLanguage.allRules = {
				"required" : { // Add your regex rules here, you can take
								// telephone as an example
					"regex" : "none",
					"alertText" : "不能为空",
					"alertTextCheckboxMultiple" : "* 必须选择一个选项",
					"alertTextCheckboxe" : "* This checkbox is required"
				},
				"exemptString" : {
					"alertText" : "不允许输入该值"
				},
				"minSize" : {
					"regex" : "none",
					"alertText" : "* Minimum ",
					"alertText2" : " characters allowed"
				},
				"maxSize" : {
					"regex" : "none",
					"alertText" : "最大允许输入 ",
					"alertText2" : " 个字符"
				},
				"min" : {
					"regex" : "none",
					"alertText" : "* Minimum value is "
				},
				"max" : {
					"regex" : "none",
					"alertText" : "* Maximum value is "
				},
				"length" : {
					"regex" : "none",
					"alertText" : "*Between ",
					"alertText2" : " and ",
					"alertText3" : " characters allowed"
				},
				"maxCheckbox" : {
					"regex" : "none",
					"alertText" : "* Checks allowed Exceeded"
				},
				"minCheckbox" : {
					"regex" : "none",
					"alertText" : "* Please select ",
					"alertText2" : " options"
				},
				"confirm" : {
					"regex" : "none",
					"alertText" : "* Your field is not matching"
				},
				"telephone" : {
					"regex" : /^[0-9\-\(\)\ ]+$/,
					"alertText" : "电话号码输入不正确"
				},
				"postcode" : {
					"regex" : /^([0-9]{6})?$/,
					"alertText" : "邮政编码输入不正确"
				},
				"email" : {
					"regex" : /^([a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\.)+[a-zA-Z0-9]{2,4})?$/,
					"alertText" : "邮箱地址输入不正确"
				},
				"date" : {
					"regex" : /^[0-9]{4}\-[0-9]{1,2}\-[0-9]{1,2}$/,
					"alertText" : "日期格式必须为YYYY-MM-DD."
				},
				"number" : {
					// Number, including positive, negative, and floating
					// decimal. credit: orefalo
					"regex" : /^[\-\+]?(([0-9]+)([\.,]([0-9]+))?|([\.,]([0-9]+))?)$/,
					"alertText" : "请输入正确的数字"
				},
				"onlyNumber" : {
					"regex" : /^[0-9]*$/,
					"alertText" : "请输入正整数"
				},
				"noSpecialCaracters" : {
					"regex" : "/^[0-9a-zA-Z]+$/",
					"alertText" : "* No special caracters allowed"
				},
				"ajaxUser" : {
					"file" : "validateUser.php",
					"extraData" : "name=eric",
					"alertTextOk" : "* This user is available",
					"alertTextLoad" : "* Loading, please wait",
					"alertText" : "* This user is already taken"
				},
				"ajaxName" : {
					"file" : "validateUser.php",
					"alertText" : "* This name is already taken",
					"alertTextOk" : "* This name is available",
					"alertTextLoad" : "* Loading, please wait"
				},
				"onlyLetter" : {
					"regex" : /^[a-zA-Z\ \']+$/,
					"alertText" : "* Letters only"
				},
				"validate2fields" : {
					"nname" : "validate2fields",
					"alertText" : "* You must have a firstname and a lastname"
				},
				"commaBetweenNumber" : {
					"regex" : /^\d+(,\d+)*$/,
					"alertText" : "* 必须包含数字并以逗号分割"
				},
				"time2time" : {
					"regex" : /^[0-9]{2}\:[0-9]{2}\-[0-9]{2}\:[0-9]{2}$/,
					"alertText" : "* 格式为 HH:MI-HH:MI "
				}
			};

		}
	};
})(jQuery);

$(document).ready(function() {
	$.validationEngineLanguage.newLang();
});