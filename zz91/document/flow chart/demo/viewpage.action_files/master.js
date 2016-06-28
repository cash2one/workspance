// ============================
// = Search field placeholder =
// ============================
AJS.toInit(function () {
    var search = AJS.$("quick-search-query");
    search.placeholder = AJS.params.quickSearchPlaceholder;
    search.placeholded = true;
    search.value = search.placeholder;
    AJS.toggleClassName(search, "placeholded");

    if (!(navigator.vendor && navigator.vendor.indexOf("Apple") + 1)) {
        AJS.bind("#quick-search-query", "focus", function () {
            if (this.placeholded) {
                this.placeholded = false;
                this.value = "";
                AJS.toggleClassName(this, "placeholded");
            }
        });

        AJS.bind("#quick-search-query", "blur", function () {
            if (this.placeholder && (/^\s*$/).test(this.value)) {
                this.value = this.placeholder;
                this.placeholded = true;
                AJS.toggleClassName(this, "placeholded");
            }
        });
    } else {
        search.type = "search";
        search.setAttribute("results", 10);
        search.setAttribute("placeholder", AJS.params.quickSearchPlaceholder);
        search.value = "";
    }
});
