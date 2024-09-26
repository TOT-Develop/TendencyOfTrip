let currentQuestion = 1;
const results = {
    힐링여행자: 0,
    탐험가: 0,
    낭만주의자: 0,
    귀차니즘: 0,
    완벽주의자: 0,
    실속주의자: 0,
    자유로운영혼: 0
};

function showQuestion(questionNumber) {
    document.querySelectorAll('.question').forEach((q) => {
        q.classList.remove('active');
    });
    document.getElementById(`question${questionNumber}`).classList.add('active');
    console.log(`Showing question ${questionNumber}`); // 로그 추가
}

function selectAnswer(answer, questionNumber) {
    console.log(`Selecting answer for question ${questionNumber}: ${answer}`); // 로그 추가
    
    answer.split(',').forEach((pair) => {
        const [type, score] = pair.split(':');
        results[type] = (results[type] || 0) + parseInt(score, 10);
    });

    console.log('Current results:', results); // 로그 추가

    if (questionNumber < 7) {
        currentQuestion++;
        showQuestion(currentQuestion);
    } else {
        processResults();
    }
}

function processResults() {
    console.log('Processing results...'); // 로그 추가
    
    let resultType = '';
    let maxScore = -1;
    for (const [type, score] of Object.entries(results)) {
        if (score > maxScore) {
            maxScore = score;
            resultType = type;
        }
    }

    console.log('Final results:', results);
    console.log('Determined result type:', resultType);

    // 결과를 세션 스토리지에 저장
    sessionStorage.setItem('resultType', resultType);

    // 잠시 후 페이지 이동, 페이지가 완전히 로드되도록 지연 시간 추가
    setTimeout(() => {
        window.location.href = '/tot/tendencyTestResult'; // Adjust this URL as needed
    }, 100);
}

// Initialize the first question
showQuestion(currentQuestion);
