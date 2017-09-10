function getAjax(){
			var xhr=null;
			if(window.XMLHttpRequest){
				xhr=new XMLHttpRequest(); 
			}else{
				xhr=new ActiveXObject('MicroSoft.XMLHttp');
			}
			return xhr;
		}
function $(id){
	return document.getElementById(id);
}
function $F(id){
	return $(id).value;
}