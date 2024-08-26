function viewReview(reviewNo) {
    fetch(`/reviews/${reviewNo}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("리뷰를 찾을 수 없습니다.");
            }
            return response.json();
        })
        .then(data => {
            // 리뷰 데이터 추출
            const review = data.reivew;
            const images = data.images || [];

            // 제목과 내용 업데이트
            document.getElementById("viewTitle").textContent = review.reviewTitle;
            document.getElementById("viewContent").textContent = review.reviewContent;

            // 이미지 슬라이더 초기화
            const viewImages = document.getElementById("viewImages");
            viewImages.innerHTML = ''; // 기존 이미지를 제거합니다.

            images.forEach(image => {
                const img = document.createElement("img");
                img.src = `${image.imagePath}`; // 이미지 URL이 어떻게 제공되는지에 따라 경로 조정 필요
                img.alt = `${image.imageName}`;
                img.addEventListener("click", () => {
                    openImageModal(img.src);
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

// 이미지 확대 모달
function openImageModal(imageUrl) {
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

// 리뷰 작성 팝업 열기
function openPostPopup() {
    document.getElementById("postPopup").style.display = "flex";
}

// 리뷰 작성 팝업 닫기
function closePostPopup() {
    document.getElementById("postPopup").style.display = "none";
}
