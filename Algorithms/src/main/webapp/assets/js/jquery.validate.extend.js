jQuery.validator.addMethod("mobile", function(value, element){var length = value.length;return this.optional(element) || length == 11 && /^1[3578]\d{9}$/.test(value);}, "请输入正确的手机号码");
jQuery.validator.addMethod("landline", function(value, element) {var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/; return this.optional(element) || (tel.test(value));}, "请输入正确的座机号码,如有区号以中划线分隔");


jQuery.validator.addMethod("idCard", function(value, element) {
	//alert("idCard validate exe");
	var aCity = {
		11 : "北京",
		12 : "天津",
		13 : "河北",
		14 : "山西",
		15 : "内蒙古",
		21 : "辽宁",
		22 : "吉林",
		23 : "黑龙江",
		31 : "上海",
		32 : "江苏",
		33 : "浙江",
		34 : "安徽",
		35 : "福建",
		36 : "江西",
		37 : "山东",
		41 : "河南",
		42 : "湖北",
		43 : "湖南",
		44 : "广东",
		45 : "广西",
		46 : "海南",
		50 : "重庆",
		51 : "四川",
		52 : "贵州",
		53 : "云南",
		54 : "西藏",
		61 : "陕西",
		62 : "甘肃",
		63 : "青海",
		64 : "宁夏",
		65 : "新疆",
		71 : "台湾",
		81 : "香港",
		82 : "澳门",
		91 : "国外"
	};
	
	if (this.optional(element)) {
		return true;
	}
	
	var iSum = 0, sBirthday;
	var idCardLength = value.length;
	
	if (!/^\d{17}(\d|x)$/i.test(value) && !/^\d{15}$/i.test(value)) {
		// alert("非法证号");
		return false;
	}

	// 在后面的运算中x相当于数字10,所以转换成a
	value = value.replace(/x$/i, "a");

	if (aCity[parseInt(value.substr(0, 2))] == null) {
		// alert("非法地区");
		return false;
	}

	if (idCardLength == 18) {
		sBirthday = value.substr(6, 4) + "-" + Number(value.substr(10, 2)) + "-" + Number(value.substr(12, 2));
		var d = new Date(sBirthday.replace(/-/g, "/"));
		if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate())) {
			// alert("非法生日");
			return false;
		}

		for (var i = 17; i >= 0; i--)
			iSum += (Math.pow(2, i) % 11) * parseInt(value.charAt(17 - i), 11);

		if (iSum % 11 != 1) {
			// alert("非法证号");
			return false;
		}
	} else if (idCardLength == 15) {
		sBirthday = "19" + value.substr(6, 2) + "-" + Number(value.substr(8, 2)) + "-" + Number(value.substr(10, 2));
		var d = new Date(sBirthday.replace(/-/g, "/"));
		var dd = d.getFullYear().toString() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
		if (sBirthday != dd) {
			// alert("非法生日");
			return false;
		}
	}
	return true;
	
}, "请输入正确的身份证号码");

/*
jQuery.validator.addMethod("picSuffix", function(value, element) {
	//return this.optional(element) || (tel.test(value));
	alert("picSuffix validate exe");
	//alert(value);
	if (value && value.length > 0) {
		var suffix = value.substr(value.lastIndexOf(".") + 1);
		suffix = suffix.toLowerCase( );
		if (suffix != "jpg" && suffix != "jpeg" && suffix != "bmp") {
	        return false;
		} else {
			return true;
		}
	} else {
		return false;
	}
}, "暂只支持 jpg,jpeg,bmp 类型的图片");
*/
