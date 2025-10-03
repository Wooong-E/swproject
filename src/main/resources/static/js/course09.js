document.addEventListener('DOMContentLoaded', function () {
    // These variables are made available by the inline script in the HTML
    // const startAddress = ...;
    // const selectedPlaces = ...;

    const mapContainer = document.getElementById('map');
    if (!mapContainer) {
        console.error('Map container not found!');
        return;
    }

    const mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // Default: Seoul City Hall
        level: 8
    };
    const map = new kakao.maps.Map(mapContainer, mapOption);
    const geocoder = new kakao.maps.services.Geocoder();

    // Create a list of all points to geocode, with names and addresses
    const pointsToGeocode = [
        { name: '출발지', address: startAddress },
        ...selectedPlaces.map(place => ({ name: place.name, address: place.address }))
    ];

    // Convert all addresses to coordinates
    const geocodePromises = pointsToGeocode.map(point => {
        return new Promise((resolve, reject) => {
            geocoder.addressSearch(point.address, function (result, status) {
                if (status === kakao.maps.services.Status.OK) {
                    resolve({
                        name: point.name,
                        address: point.address,
                        coords: new kakao.maps.LatLng(result[0].y, result[0].x)
                    });
                } else {
                    // Resolve with null to handle partial failures gracefully
                    console.warn('Geocoding failed for: ' + point.address);
                    resolve(null);
                }
            });
        });
    });

    // Once all points are geocoded, process them
    Promise.all(geocodePromises)
        .then(results => {
            const geocodedPoints = results.filter(p => p !== null); // Filter out failed geocodes

            if (geocodedPoints.length < 1) {
                alert('유효한 주소를 찾을 수 없어 지도를 표시할 수 없습니다.');
                return;
            }

            // Separate the fixed start point from the destinations
            const startPoint = geocodedPoints.shift();
            const destinations = geocodedPoints;

            // Find the shortest path for the destinations, starting from the startPoint
            const orderedDestinations = findShortestPath(startPoint, destinations);

            // Re-add start point to the beginning of the final path
            const finalPath = [startPoint, ...orderedDestinations];

            // Draw markers and polyline on the map
            drawPath(finalPath);
        })
        .catch(error => {
            console.error('Map generation error:', error);
            alert('지도를 생성하는 중 오류가 발생했습니다.');
        });

    // --- Helper functions (adapted from mapapi.html) ---

    function getDistance(latlng1, latlng2) {
        function toRad(value) { return value * Math.PI / 180; }
        const R = 6371; // km
        const dLat = toRad(latlng2.getLat() - latlng1.getLat());
        const dLon = toRad(latlng2.getLng() - latlng1.getLng());
        const lat1 = toRad(latlng1.getLat());
        const lat2 = toRad(latlng2.getLat());
        const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                  Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    function findShortestPath(start, points) {
        if (!points || points.length === 0) return [];
        let path = [];
        let unvisited = [...points];
        let currentPoint = start;

        while (unvisited.length > 0) {
            let nearestIndex = -1;
            let nearestDistance = Infinity;

            for (let i = 0; i < unvisited.length; i++) {
                const distance = getDistance(currentPoint.coords, unvisited[i].coords);
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestIndex = i;
                }
            }

            currentPoint = unvisited.splice(nearestIndex, 1)[0];
            path.push(currentPoint);
        }
        return path;
    }

    function drawPath(path) {
        const bounds = new kakao.maps.LatLngBounds();
        const linePath = [];

        path.forEach((p, index) => {
            linePath.push(p.coords);
            bounds.extend(p.coords);

            const marker = new kakao.maps.Marker({
                map: map,
                position: p.coords
            });

            const infowindow = new kakao.maps.InfoWindow({
                content: `<div style="padding:5px;font-size:12px;text-align:center;">${index + 1}<br>${p.name}</div>`,
                disableAutoPan: true
            });
            infowindow.open(map, marker);
        });

        const polyline = new kakao.maps.Polyline({
            path: linePath,
            strokeWeight: 4,
            strokeColor: '#FF0000',
            strokeOpacity: 0.8,
            strokeStyle: 'solid'
        });
        polyline.setMap(map);

        map.setBounds(bounds);
    }
});
