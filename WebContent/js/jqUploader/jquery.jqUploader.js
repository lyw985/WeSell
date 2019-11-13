/**
 * jqUploader (http://www.pixeline.be/experiments/jqUploader/)
 * A jQuery plugin to replace html-based file upload input fields with richer flash-based upload progress bar UI.
 *
 * Version 1.0.2.3
 * December 2010
 *
 * Copyright (c) 2007 Alexandre Plennevaux (http://www.pixeline.be)
 * Dual licensed under the MIT and GPL licenses.
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.opensource.org/licenses/gpl-license.php
 *
 * requires plugin "Flash" by Luke Lutman (http://jquery.lukelutman.com/plugins/flash)
 *
 * IMPORTANT:
 * The packed version of jQuery breaks ActiveX control
 * activation in Internet Explorer. Use JSMin to minifiy
 * jQuery (see: http://jquery.lukelutman.com/plugins/flash#activex).
 *
 **/
jQuery.fn.jqUploader = function(options) {
    return this.each(function(index) {
        var $this = jQuery(this);
        var $thisInput = $("input[type='file']",$this);
        var containerId = $this.attr("id");
        if (containerId == null || containerId.length <= 0){
        	containerId = 'jqUploader-'+(new Date().getTime())+"-"+Math.round(Math.random()*100);
        	$this.attr("id",containerId);
        }
        var opts = jQuery.extend({
                debug: 0,
                width:320,
                height:85,
                version: 8, // version 8+ of flash player required to run jqUploader
                background: 'f2f5f7', // background color of flash file
                src:    'jqUploader.swf',
                uploadUrl:     null,
                afterUploaded:      null,
                varName:	        $thisInput.attr("name"),  //this holds the variable name of the file input field in your html form
                allowedExt:	      '*.jpg; *.jpeg; *.png', // allowed extensions
                allowedExtDescr:  'Images (*.jpg; *.jpeg; *.png)',
                params:           {
                    menu:false
                },
                flashvars:        {},
                barColor:		      '0000CC',
                maxFileSize:      0,
                startMessage:     '请选择文件：',
                errorSizeMessage: '文件过大!',
                validFileMessage: '点击[上传]按钮开始上传!',
                progressMessage:  '正在上传,请稍候...',
                endMessage:       '完成!',
                autoUpload :      0, //Auto start uploading.
                replaceByHidden: true //使用隐藏域替换控件
            }, options || {}
        );
        $this.data('opts',opts);

        var myParams = '';
        for (var p in opts.params){
            myParams += p+'='+opts.params[p]+',';
        }
        myParams = myParams.substring(0, myParams.length-1);
        // this function interfaces with the jquery flash plugin
        jQuery(this).flash(
            {
                src: opts.src,
                width: opts.width,
                height: opts.height,
                id:'movie_player-'+containerId,
                bgcolor:'#'+opts.background,
                flashvars: {
                    debug: 				opts.debug,
                    containerId: 		containerId,
                    uploadScript: 		opts.uploadUrl,
                    allowedExt: 		opts.allowedExt,
                    allowedExtDescr: 	opts.allowedExtDescr,
                    varName :  			opts.varName,
                    barColor : 			opts.barColor,
                    maxFileSize :		opts.maxFileSize,
                    startMessage : 		opts.startMessage,
                    errorSizeMessage : 	opts.errorSizeMessage,
                    validFileMessage : 	opts.validFileMessage,
                    progressMessage : 	opts.progressMessage,
                    endMessage: 		opts.endMessage,
                    autoUpload: 		opts.autoUpload
                },
                params: myParams
            },
            {
                version: opts.version,
                update: false
            },
            function(htmlOptions){
                var $el = $.fn.flash.transform(htmlOptions);
                $(this).empty().append($el);
            }
        );
    });
};
jQuery.fn.jqUploader.Terminate=function(containerId,filename,data,varname){
	$this= $('#'+containerId);
	var opts = $this.data('opts');
	//使用隐藏域替换组件
	if(opts.replaceByHidden){
		$this.empty().append('<input name="'+varname+'" type="hidden" id="'+varname+'" value="'+data+'"/><label>'+filename+'</label>');
	}
    if(opts != null && $.isFunction(opts.afterUploaded)){
    	opts.afterUploaded.call($this,filename,data);
    }
};
