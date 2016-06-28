// ==================
// = Drop-down menu =
// ==================
AJS.menuShowCount = 0;

AJS.toInit(function () {
    AJS.$$("ajs-menu-bar", undefined, function (el) {
        var hideDropDown = function (e) {
            if (typeof AJS.dropDownTimer != "undefined" && AJS.dropDownHider) {
                clearTimeout(AJS.dropDownTimer);
                delete AJS.dropDownTimer;
                AJS.dropDownHider();
                AJS.dropDownHider = null;
            }
        };
        AJS.$$("ajs-button", el, function (it) {
            AJS.Event.addListener(it, "mouseover", hideDropDown);
        });
        AJS.$$("ajs-menu-item", el, function (it) {
            var dd = AJS.$$("ajs-drop-down", it);
            if (!dd.length) return;

            dd = dd[0];
            dd.hidden = true;
            dd.focused = -1;
            dd.hide = function () {
                if (!this.hidden) {
                    AJS.toggleClassName(it, "opened");
                    var as = this.getElementsByTagName("a");
                    AJS.toggleClassName(this, "hidden");
                    this.hidden = true;
                    AJS.Event.removeListener(document, "click", this.hide);
                    AJS.Event.removeListener(document, "keydown", this.movefocus);
                    AJS.Event.removeListener(document, "keypress", this.blocker);
                    if (this.focused + 1) {
                        AJS.Dom.removeClass(as[this.focused], "active");
                    }
                    this.focused = -1;
                }
            };
            dd.show = function () {
                if (typeof this.hidden == "undefined" || this.hidden) {
                    AJS.toggleClassName(this, "hidden");
                    AJS.toggleClassName(it, "opened");
                    this.hidden = false;
                    var dd = this;
                    this.timer = setTimeout(function () {AJS.Event.addListener(document, "click", dd.hide, dd, true);}, 1);
                    AJS.Event.addListener(document, "keydown", this.movefocus, this, true);
                    AJS.Event.addListener(document, "keypress", this.blocker, this, true);
                    var as = dd.getElementsByTagName("a");
                    for (var i = 0, ii = as.length; i < ii; i++) {
                        AJS.Event.addListener(as[i], "mouseover", (function (j) {
                            return function () {
                                if (this.parentNode.parentNode.focused + 1) {
                                    AJS.Dom.removeClass(as[this.parentNode.parentNode.focused].parentNode, "active");
                                }
                                AJS.Dom.addClass(this.parentNode, "active");
                                this.parentNode.parentNode.focused = j;
                            };
                        })(i));
                        AJS.Event.addListener(as[i], "mouseout", function () {
                                if (this.parentNode.parentNode.focused + 1) {
                                    AJS.Dom.removeClass(as[this.parentNode.parentNode.focused].parentNode, "active");
                                }
                                this.parentNode.parentNode.focused = -1;
                            });
                    }
                }
            };
            dd.blocker = function (e) {
                var c = AJS.Event.getCharCode(e);
                if (c == 40 || c == 38) {
                    AJS.Event.stopEvent(e);
                }
            };
            dd.movefocus = function (e) {
                var c = AJS.Event.getCharCode(e),
                    a = this.getElementsByTagName("a");
                if (this.focused + 1) {
                    AJS.Dom.removeClass(a[this.focused].parentNode, "active");
                }
                switch (c) {
                    case 40:
                    case 9: {
                        this.focused++;
                        break;
                    }
                    case 38: {
                        this.focused--;
                        break;
                    }
                    case 27: {
                        this.hide();
                        return false;
                    }
                    default: {
                        return true;
                    }
                }
                if (this.focused < 0) {
                    this.focused = a.length - 1;
                }
                if (this.focused > a.length - 1) {
                    this.focused = 0;
                }
                a[this.focused].focus();
                AJS.Dom.addClass(a[this.focused].parentNode, "active");
                AJS.Event.stopEvent(e);
            };
            dd.show();
            clearTimeout(dd.timer);
            dd.region = AJS.Dom.getRegion(dd);
            dd.hide();
            if (dd.region.right > AJS.Dom.getViewportWidth()) {
                var it_region = AJS.Dom.getRegion(it);
                dd.style.marginLeft = "-" + ((dd.region.right - dd.region.left) - (it_region.right - it_region.left)) + "px";
            }
            var a = AJS.$$("trigger", it);
            if (a) {
                var killHideTimerAndShow = function() {
                    clearTimeout(AJS.dropDownTimer);
                    delete AJS.dropDownTimer;
                    AJS.dropDownHider();
                    AJS.dropDownHider = null;
                    dd.show();
                };

                var overHandler = function (e) {
                    var changingMenu = typeof AJS.dropDownTimer != "undefined";
                    if (changingMenu) {
                        killHideTimerAndShow();
                    }
                    else {
                        AJS.dropDownShower = function () {dd.show(); delete AJS.dropDownShowerTimer;};
                        AJS.dropDownShowerTimer = setTimeout(AJS.dropDownShower, 500);
                    }
                };
                var outHandler = function (e) {
                    var passingThrough = typeof AJS.dropDownShowerTimer != "undefined";
                    if (passingThrough) {
                        clearTimeout(AJS.dropDownShowerTimer);
                        delete AJS.dropDownShowerTimer;
                    }
                    if (typeof AJS.dropDownTimer != "undefined") {
                        clearTimeout(AJS.dropDownTimer);
                        delete AJS.dropDownHider;
                    }
                    AJS.dropDownHider = function () {dd.hide(); delete AJS.dropDownTimer;};
                    AJS.dropDownTimer = setTimeout(AJS.dropDownHider, 300);
                };
                //AJS.Event.addListener(a, "mouseover", overHandler);
                AJS.Event.addListener(a, "click", function (e) { return AJS.stopEvent(e); });
                // AJS.Event.addListener(it, "click", function () {dd.show();});
                AJS.Event.addListener(it, "mouseover", overHandler);
                AJS.Event.addListener(it, "mouseout", outHandler);
            }
        });
    });

    /* TODO: Restore this once JQuery is integrated and HTMLUnit is upgraded to work with JQuery. */
    /*jQuery(function ($) {
        $(".popup-link").bind("click", function() {
            window.open(this.href, this.id + '-popupwindow', 'width=600, height=400, scrollbars, resizable');
            return false;
        });
    });*/

    var ids = ["action-view-source-link", "view-user-history-link"];
    for (var i=0; i<ids.length; i++) {
        AJS.bind(AJS.$(ids[i]), function(e) {
            window.open(this.href, this.id + '-popupwindow', 'width=600, height=400, scrollbars, resizable');
            return AJS.stopEvent(e);
        });
    }

    var favourite = AJS.$("page-favourite");
    AJS.bind(favourite, function(e) {
        AJS.Event.stopEvent(e);
        AJS.Dom.addClass(favourite, "waiting");
        var params = {
            callback: function () {
                AJS.Dom.removeClass(favourite, "waiting");
                AJS.toggleClassName(favourite, "selected");
                AJS.toggleClassName(favourite, "ie-page-favourite-selected");
            },
            errorHandler: function () {
                AJS.log("Error updating favourite");
            }
        };
        if (!AJS.Dom.hasClass(favourite, "selected")) {
            AddLabelToEntity.addFavourite(AJS.params.pageId, params);
        }
        else {
            RemoveLabelFromEntity.removeFavourite(AJS.params.pageId, params);
        }
    });

    var watch = AJS.$("page-watch");
    AJS.bind(watch, function(e) {
        AJS.Event.stopEvent(e);
        AJS.Dom.addClass(watch, "waiting");
        var params = {
            callback: function () {
                AJS.Dom.removeClass(watch, "waiting");
                AJS.toggleClassName(watch, "selected");
                AJS.toggleClassName(watch, "ie-page-watching-selected");
            },
            errorHandler: function () {
                AJS.log("Error updating watch");
            }
        };
        if (!AJS.Dom.hasClass(watch, "selected")) {
            PageNotification.startWatching(AJS.params.pageId, params);
        }
        else {
            PageNotification.stopWatching(AJS.params.pageId, params);
        }
    });
});