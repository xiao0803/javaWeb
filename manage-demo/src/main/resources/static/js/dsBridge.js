function getJsBridge() {
	return {
		call : function(e, c, a) {
			var b = '';
			if (typeof c == 'function') {
				a = c;
				c = {}
			}
			if (typeof a == 'function') {
				window.dscb = window.dscb || 0;
				var d = 'dscb' + window.dscb++;
				window[d] = a;
				c._dscbstub = d
			}
			c = JSON.stringify(c || {});
			if (window._dswk) {
				b = prompt(window._dswk + e, c)
			} else {
				if (typeof _dsbridge == 'function') {
					b = _dsbridge(e, c)
				} else {
					b = _dsbridge.call(e, c)
				}
			}
			return b
		}
	}
};