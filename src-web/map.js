var BitCtrl;
(function (BitCtrl) {
    var Leaflet;
    (function (Leaflet) {
        var Map = (function () {
            function Map(properties) {
                this.isInitialized = false;
                this.initTasks = Array();

                var _this = this;
                var element = document.createElement("div");
                element.style.width = "100%";
                element.style.height = "100%";
                var parent = rap.getObject(properties.parent);
                parent.append(element);
                this.map = L.map(element);
                L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
                	attribution: "&copy; OpenStreetMap contributors"
                }).addTo(this.map);

                parent.addListener("Resize", function (event) {
                	console.log(
                			"Resize", event,
                			"Bounds", parent.getClientArea(),
                			"Element bounding client rect", element.getBoundingClientRect()
                	);
                	return _this.invalidateSize();
                });
                var onRendered = function () {
                    rap.off("render", onRendered);
                    _this.invalidateSize();
                    _this.isInitialized = true;
                    _this.initTasks.forEach(function (it) { return it(); });
                };
                rap.on("render", onRendered);
            }
            Map.prototype.invalidateSize = function () {
            	var _this = this;
                setTimeout(function() { _this.map.invalidateSize(false); }, 100);
            };
            Map.prototype.setView = function (parameters) {
            	var _this = this;
                if (!this.isInitialized) {
                    this.initTasks.push(function () { return _this.setView(parameters); });
                    return;
                }
                this.map.setView(parameters.center, parameters.zoom);
            };
            Map.prototype.destroy = function () {
                this.map.remove();
            };
            return Map;
        }());
        Leaflet.Map = Map;
    })(Leaflet = BitCtrl.Leaflet || (BitCtrl.Leaflet = {}));
})(BitCtrl || (BitCtrl = {}));
rap.registerTypeHandler("BitCtrl.Leaflet.Map", {
    factory: function (properties) { return new BitCtrl.Leaflet.Map(properties); },
    methods: ["setView"],
    destructor: "destroy"
});
