
    document.addEventListener('DOMContentLoaded', () => {
        // 서버에서 memId를 가져오는 API 호출
        fetch('session/id')
            .then(response => response.json())
            .then(data => {
                const memId = data.memId || 'guest';  // memId가 없으면 'guest'로 기본값 설정
                sessionStorage.setItem('memId', memId); // 세션에 memId 저장
                
                // 페이지 로드 후 결과 업데이트
                const resultType = sessionStorage.getItem('resultType') || '기본 성향';
                updateResult(resultType);
            });
    });

    function updateResult(resultType) {
        const resultData = {
            '힐링여행자': {
                image: `${basePath}/static/image/testResult_healing.png`,
                description: '답답한 회색 도시를 벗어나는 여행을 꿈꾸고\n 진정한 휴식과 치유를 갈망하는 힐링이 필요한 여행자\n여행할 때 만큼은 모든 고민을 잠시 내려두고, 온전히 나 자신에게 집중하고 노력하는 편'
            },
            '탐험가': {
                image: `${basePath}/static/image/testResult_explorer.png`,
                description: '마음만은 로컬 인공적인 것보다는\n 대자연이 빚은 절경을 동경하고 찾아다니는 탐험가 정신의 소유자\n호기심 MAX! 평소에도 그렇지만, 여행지에선 특히 새롭고, 쉽게 경험할 수 없는 것을 찾는 편'
            },
            '낭만주의자': {
                image: `${basePath}/static/image/testResult_romanticist.png`,
                description: '"이 조명, 공기, 습도.." 여행하면 낭만, 낭만하면 여행!\n아름다운 풍경과 분위기면 모든 피로가 풀리는 낭만적인 영혼의 소유자\n대단한 걸 하지 않더라도, 핑크 빛으로 물든 저녁 하늘 하나만으로도 감성에 젖으며 여행만족도가 MAX에 달하는 편'
            },
            '귀차니즘': {
                image: `${basePath}/static/image/testResult_annoying.png`,
                description: '덥고 추울 때 돌아다니는 것보다는 호캉스가,\n 호캉스 보다는 편안한 내 방이 최고인 집돌/집순이\n막상 여행을 가면 누구에게도 뒤지지 않게 잘 놀지만,\n 집 밖으로 나서는 것을 제일 귀찮고 어려워하는 편'
            },
            '완벽주의자': {
                image: `${basePath}/static/image/testResult_perfectionist.png`,
                description: '"이게 바로 내 여행이지!"\n 계획을 좋아하는 당신은 어디를 나서더라도 확실한 일정을 계획하는 완벽주의자입니다.\n 여행에도 모든 일정을 미리 정해 하고 싶은걸 미리 정해서 계획적으로 움직이고 일정을 완벽히 소화 했을 때 여행만족도가 MAX에 달하는 편'
            },
            '실속주의자': {
                image: `${basePath}/static/image/testResult_practical.png`,
                description: '예산안에서 완벽하게 즐기고 싶은 당신은 실속주의자입니다.\n자신이 정한 예산안에서 하고 싶은 일정을 다 소화 했을 때\n 여행만족도가 MAX에 달하는 편'
            },
            '자유로운영혼': {
                image: `${basePath}/static/image/testResult_freeSpirit.png`,
                description: '사교적이고 활동적이며 낙천적인 당신은 자유로운 영혼의 소유자입니다.\n일정을 정하지 않고 자유롭게 누비며 처음만난 사람과도 마치 친한 친구처럼 대할 수 있습니다.\n여행 중 예기치 못했던 극한 상황이 오더라도, "오히려 좋아" 긍정 에너지를 뿜어내는 편'
            }
        };

        const data = resultData[resultType] || { image: '', description: '결과에 대한 설명이 없습니다.' };

        document.getElementById('resultType').innerText = resultType;
        document.getElementById('resultImage').src = data.image;
        document.getElementById('resultDescription').innerText = data.description;
    }

    document.getElementById('saveResultBtn').addEventListener('click', () => {
        const resultType = document.getElementById('resultType').innerText;
        const memId = sessionStorage.getItem('memId') || 'guest';

        saveResult(resultType, memId);
    });

    function saveResult(resultType) {
    const memId = sessionStorage.getItem('memId');
    console.log(memId);

    if (memId === 'guest') {
        const userConfirmed = confirm('로그인 후 성향 결과를 저장할 수 있습니다. 로그인 페이지로 이동하시겠습니까?');

        if (userConfirmed) {
            // 현재 페이지의 URL을 서버에 저장하기 위한 AJAX 요청
            fetch('/tot/setReturnUrl', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ returnUrl: window.location.href })
            }).then(() => {
                // 로그인 페이지로 리디렉션
                window.location.href = 'login';
            }).catch(error => {
                console.error('Fetch error:', error);
            });
        } else {
            return;
        }
    } else {
        fetch('/tot/saveTendencyTestResult', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                resultType: resultType,
                memId: memId
            })
        }).then(response => response.json())
          .then(data => {
              if (data.success) {
                  alert('성향 결과가 저장되었습니다.');
              } else {
                  alert('오류 발생: ' + data.error);
              }
          }).catch(error => {
              console.error('Fetch error:', error);
              alert('서버와의 통신 중 오류가 발생했습니다.');
          });
    }
}


    function getRecommendation() {
        // resultType을 세션에 저장
        const resultType = document.getElementById('resultType').innerText;
        sessionStorage.setItem('resultType', resultType);

        // 여행지 추천 페이지로 리디렉션
        window.location.href = '/tot/recommendCourseInput'; // 추천 페이지의 URL로 리디렉션
    }
