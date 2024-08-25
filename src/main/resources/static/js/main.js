document.addEventListener("DOMContentLoaded", function() {

    // 검색 버튼 클릭 이벤트
    document.getElementById("btnNavbarSearch").addEventListener("click", function() {
        searchReviews();
    });

    // 팝업 외부 클릭 시 닫기
    document.querySelectorAll('.popup').forEach(popup => {
        popup.addEventListener("click", function(event) {
            if (event.target === this) {
                this.style.display = "none";
            }
        });
    });
});

function searchReviews() {
    const query = document.getElementById("searchInput").value;
    alert("검색 기능은 아직 구현되지 않았습니다. 검색어: " + query);
}

function openPostPopup() {
    document.getElementById("postPopup").style.display = "flex";
}

function closePostPopup() {
    document.getElementById("postPopup").style.display = "none";
}


function viewReview(reviewNo) {
    fetch(`/reviews/${reviewNo}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("리뷰를 찾을 수 없습니다.");
            }
            return response.json();
        })
        .then(review => {
            // 제목과 내용 업데이트
            document.getElementById("viewTitle").textContent = review.reviewTitle;
            document.getElementById("viewContent").textContent = review.reviewContent;

            // 이미지 슬라이더 초기화
            const images = review.images || [];
            images.forEach(imageUrl => {
                            const img = document.createElement("img");
                            img.src = imageUrl;
                            img.alt = "Review Image";
                            img.addEventListener("click", () => {
                                openImageModal(imageUrl);
                            });
                            viewImages.appendChild(img);
                        });

                        document.getElementById("viewPopup").style.display = "flex";
                    })
                    .catch(error => {
                        alert(error.message);
                    });
}

function closeViewPopup() {
    document.getElementById("viewPopup").style.display = "none";
}

// 이미지 확대 모달 (선택 사항)
function openImageModal(imageUrl) {
    // 여기에 이미지 확대 모달을 구현할 수 있습니다.
    const imageModal = document.createElement("div");
    imageModal.style.position = "fixed";
    imageModal.style.top = "0";
    imageModal.style.left = "0";
    imageModal.style.width = "100%";
    imageModal.style.height = "100%";
    imageModal.style.backgroundColor = "rgba(0, 0, 0, 0.8)";
    imageModal.style.display = "flex";
    imageModal.style.justifyContent = "center";
    imageModal.style.alignItems = "center";
    imageModal.style.zIndex = "1001";
    imageModal.innerHTML = `
        <img src="${imageUrl}" style="max-width: 90%; max-height: 90%;">
    `;
    imageModal.addEventListener("click", () => {
        document.body.removeChild(imageModal);
    });
    document.body.appendChild(imageModal);
}
