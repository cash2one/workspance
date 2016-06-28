/**
 * Atlassian JS animation framework. Simple, but not overly so.
 *
 * TODO: Document this, make it an object
 */
AJS.animation = {
    running: [],
    queue: [],
    timer: null,
    duration: 300,
    period: 20,
    add: function(item) {
        this.queue.push(item);
    },
    start: function() {
        if (this.timer != null) return;
        this.running = this.queue;
        this.queue = [];
        this.running.each(function(i) {
            if (i.onStart) i.onStart();
        });
        var animation = this;
        var startTime = new Date().getTime();
        var endTime = startTime + this.duration;
        this.timer = setInterval(function() {
            var time = new Date().getTime();
            var pos = (time - startTime) / (endTime - startTime);
            if (pos <= 1)
                animation.animate(pos);
            if (pos >= 1 && animation.timer != null)
                animation.finish();
        }, this.period);
        return this.timer;
    },
    finish: function() {
        clearInterval(this.timer);
        this.running.each(function(i) {
            if (i.onFinish) i.onFinish();
        });
        this.running = [];
        this.timer = null; // must be last because it's the lock to prevent concurrent executions
        if (this.queue.length > 0) this.start();
    },
    animate: function(pos) {
        this.running.each(function(i) {
            if (i.animate)
                i.animate(AJS.animation.interpolate(pos, i.start, i.end, i.reverse));
        });
    },
    interpolate: function(pos, start, end, reverse)
    {
        if (typeof start != "undefined" && typeof end != "undefined")
        {
            if (reverse)
                return end + pos * (start - end);
            else
                return start + pos * (end - start);
        }
        return pos;
    },
    combine: function(list) {
        return {
            animations: list,
            append: function(animation) {
                this.animations.push(animation);
                return this;
            },
            onStart: function() {
                this.animations.each(function(i) {
                    if (i.onStart) i.onStart();
                });
            },
            onFinish: function() {
                this.animations.each(function(i) {
                    if (i.onFinish) i.onFinish();
                });
            },
            animate: function(pos) {
                this.animations.each(function(i) {
                    if (i.animate) i.animate(AJS.animation.interpolate(pos, i.start, i.end, i.reverse));
                });
            }
        };
    }
};