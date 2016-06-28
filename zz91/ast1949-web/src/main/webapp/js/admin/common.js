function checkAll(form) {

	for ( var i = 0; i < form.elements.length; i++) {
		var e = form.elements[i];
		if (e.name.substr(0, 2) == 'cb')
			// alert (e.name)
			e.checked = document.getElementById("cbAll").checked;
	}
}