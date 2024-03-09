const attendence = new Chart(
    document.getElementById('attendence'),
    config = {
    type: 'pie',
    data: data = {
        labels: [
            'NOT PARTICIPATED',
            'PARTICIPATED',
        ],
        datasets: [{
            data: [25,75],
            backgroundColor: [
            'rgb(255, 0, 0)',
            'rgb(0, 255, 0)',
            ],
            hoverOffset: 4
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        radius: '100%',
        plugins: {
        legend: {
            position: 'right',
            labels: {
                color: 'white', // Set the legend text color to black
                boxWidth: 20,
                padding: 20
            }
        }
        }
    },
    }
);
const student = new Chart(
    document.getElementById('student'),
    config = {
    type: 'pie',
    data: data = {
        labels: [
            'NOT PARTICIPATED',
            'PARTICIPATED',
        ],
        datasets: [{
            data: [25,75],
            backgroundColor: [
            'rgb(255, 0, 0)',
            'rgb(0, 255, 0)',
            ],
            hoverOffset: 4
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        radius: '100%',
        plugins: {
        legend: {
            position: 'right',
            labels: {
                color: 'white', // Set the legend text color to black
                boxWidth: 20,
                padding: 20
            }
        }
        }
    },
    }
);
const course = new Chart(
    document.getElementById('course'),
    config = {
    type: 'pie',
    data: data = {
        labels: [
            'NOT PARTICIPATED',
            'PARTICIPATED',
        ],
        datasets: [{
            data: [25,75],
            backgroundColor: [
            'rgb(255, 0, 0)',
            'rgb(0, 255, 0)',
            ],
            hoverOffset: 4
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        radius: '100%',
        plugins: {
        legend: {
            position: 'right',
            labels: {
                color: 'white', // Set the legend text color to black
                boxWidth: 20,
                padding: 20
            }
        }
        }
    },
    }
);
const absence = new Chart(
    document.getElementById('absence'),
    config = {
    type: 'pie',
    data: data = {
        labels: [
            'Donorship',
            'Sick',
            'Family'
        ],
        datasets: [{
            data: [25,65,10],
            backgroundColor: [
            'rgb(255, 0, 0)',
            'rgb(0, 255, 0)',
            'rgb(0, 0, 255)'
            ],
            hoverOffset: 4
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        radius: '100%',
        plugins: {
        legend: {
            position: 'right',
            labels: {
                color: 'white', // Set the legend text color to black
                boxWidth: 20,
                padding: 20
            }
        }
        }
    },
    }
);

function openTab(evt, tabName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(tabName).style.display = "block";
    evt.currentTarget.className += " active";
}