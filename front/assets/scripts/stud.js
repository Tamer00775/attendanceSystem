const attendence = new Chart(
    document.getElementById('attendence'),
    config = {
    type: 'pie',
    data: data = {
        datasets: [{
            data: [10,1],
            backgroundColor: [
                'rgb(0, 255, 0)',
                'rgb(255, 0, 0)',
            ],
            hoverOffset: 4
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        radius: '100%',
        plugins: {
        }
    },
    }
);
document.querySelector('.absence').addEventListener('mouseover', function() {
    document.getElementById('abb').style.display = 'block';
});

document.querySelector('.absence').addEventListener('mouseout', function() {
    document.getElementById('abb').style.display = 'none';
});

const table = document.querySelector(".table-container");
const page = document.querySelector(".att-set");
const byCode = document.querySelectorAll("#byCode");
const back = document.querySelectorAll("#back");

byCode.forEach((element) => {
    element.addEventListener("click", () => {
        table.style.display = 'none';
        page.style.display = 'block';
    });
});
back.forEach((element) => {
    element.addEventListener("click", () => {
        page.style.display = 'none';
        table.style.display = 'flex';
    });
});

const datas = [
    [
        { num: '1', date: '01.01.2023', hour: '14:00', attendance: '✓', place: 'G304' },
        { num: '2', date: '08.01.2023', hour: '14:00', attendance: 'P', place: 'G304' },
        { num: '3', date: '15.01.2023', hour: '14:00', attendance: '✓', place: 'G304' },
        { num: '4', date: '22.01.2023', hour: '14:00', attendance: '✓', place: 'G304' },
        { num: '5', date: '29.01.2023', hour: '14:00', attendance: '✗', place: 'G304' },
        { num: '6', date: '05.02.2023', hour: '14:00', attendance: '✓', place: 'G304' },
        { num: '7', date: '12.02.2023', hour: '14:00', attendance: '✓', place: 'G304' },
        { num: '8', date: '19.02.2023', hour: '14:00', attendance: '✓', place: 'G304' },
    ],
    [
        { num: '9', date: '26.02.2023', hour: '14:00', attendance: '✓', place: 'G304' },
        { num: '10', date: '03.03.2023', hour: '14:00', attendance: 'P', place: 'G304' },
        { num: '11', date: '10.03.2023', hour: '14:00', attendance: '', place: 'G304' },
        { num: '12', date: '17.03.2023', hour: '14:00', attendance: '', place: 'G304' },
        { num: '13', date: '24.03.2023', hour: '14:00', attendance: '', place: 'G304' },
        { num: '14', date: '01.04.2023', hour: '14:00', attendance: '', place: 'G304' },
        { num: '15', date: '08.04.2023', hour: '14:00', attendance: '', place: 'G304' },
        { num: '16', date: '15.04.2023', hour: '14:00', attendance: '', place: 'G304' },
    ],
];

function showPage(pageNumber) {
    show();
    f.style.display = 'flex';
    const tableBody = document.getElementById('attendance-body');
    tableBody.innerHTML = '';
    const pageData = datas[pageNumber - 1];

    pageData.forEach((record) => {
        const row = tableBody.insertRow();
        row.style = 'display: flex; flex-direction: column;  align-items: center; text-align: center; ';
        row.innerHTML = `
            <td style="width:100%">${record.num}</td>
            <td style="width:100%">${record.date}</td>
            <td style="width:100%">${record.hour}</td>
            <td style="width:100%" class="${getClassForAttendance(record.attendance)}">${record.attendance}</td>
            <td style="width:100%">${record.place}</td>
        `;
    });
}

function getClassForAttendance(attendance) {
    switch (attendance) {
        case '✓':
            return 'checkmark';
        case 'P':
            return 'late';
        case '✗':
            return 'crossmark';
        default:
            return '';
    }
}
const f = document.querySelector(".first");
const s = document.querySelector(".second");
const fe = document.querySelectorAll("#first");
const se = document.querySelectorAll("#second");

fe.forEach((element) => {
    element.addEventListener("click", () => {
        s.style.display = 'flex';
        f.style.display = 'none';
    });
});
se.forEach((element) => {
    element.addEventListener("click", () => {
        f.style.display = 'flex';
        s.style.display = 'none';
    });
});
const t = document.querySelector(".attendance-table");
function show(){
    t.style.display = 'flex';
}
function hide(){
    f.style.display = 'none';
    s.style.display = 'none';
    t.style.display = 'none';
}