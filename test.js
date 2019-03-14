// expand or hidden extra info on right side
document.querySelectorAll(".btn-expand").forEach((button) => {
    button.addEventListener("click", (e) => {
        expand(e.srcElement);
    });
});

// expand or hidden with nested block
document.querySelectorAll(".btn-expand-all-nested").forEach((button) => {
    button.addEventListener("click", (e) => {
        const self = e.srcElement;
        const grandpa = self.parentElement.parentElement;
        const number = parseInt(grandpa.getAttribute("id").substring(2));
        const offset = parseInt(grandpa.getAttribute("offset"));
        const open = (self.textContent == ">");
        let btnOpen = self.parentElement.firstElementChild;
        let isOpen = (btnOpen.firstChild.textContent == "-");

        // do for self
        if (open) {
            self.textContent = "<";
            if (!isOpen)
                expand(btnOpen);
        }
        else {  // close
            self.textContent = ">";
            if (isOpen)
                expand(btnOpen);
        }
        // do for dependency                
        for (let panel of document.querySelectorAll(".tp")) {
            if (panel.getAttribute("id").substring(2) > number) {
                // only this dependecy
                if (panel.getAttribute("offset") <= offset)
                    break;
                // only 1 deep
                if (panel.getAttribute("offset") > offset + 1)
                    continue;
                btnOpen = panel.querySelector(".btn-expand");
                isOpen = btnOpen.firstChild.textContent == "-";
                if (open ? !isOpen : isOpen)    // open XOR isOpen
                    expand(btnOpen);
            }
        };
    });
});

// show dependent blocks 
document.querySelectorAll(".btn-details").forEach((button) => {
    button.addEventListener("click", (e) => {
        show(e.srcElement);
    });
});

// show all dependent blocks without false or error 
document.querySelectorAll(".btn-details-all").forEach((button) => {
    button.addEventListener("click", (e) => {
        show(e.srcElement);
    });
});

function show(srcButton) {
    const all = srcButton.classList.contains("btn-details-all");
    const blockSorce = srcButton.parentElement.parentElement.parentElement;
    const number = parseInt(blockSorce.getAttribute("id").substring(2));
    const offset = parseInt(blockSorce.getAttribute("offset"));
    const open = (all) ? (srcButton.textContent == "VVV") : (srcButton.textContent == "V");
    let limit = 1000;
    let currentNumber;
    let currentBtn;

    for (let block of document.querySelectorAll('div[offset="' + offset + '"]'))
        if ((currentNumber = parseInt(block.getAttribute("id").substring(2))) > number)
            limit = currentNumber;
    if (all) {
        if (open){
            srcButton.textContent = "AAA";
            blockSorce.querySelector(".btn-details").textContent = "A";
        }
        else {
            srcButton.textContent = "VVV";
            blockSorce.querySelector(".btn-details").textContent = "V";
        }
    }
    else {
        if (open)
            srcButton.textContent = "A";
        else { // close all 
            srcButton.textContent = "V";
            currentBtn = blockSorce.querySelector(".btn-details-all");
                if (currentBtn) // update if have button for all
                    currentBtn.textContent = "VVV"

        }
    }
    // test all 1 level higher
    for (let testBlok of document.querySelectorAll('div[offset="' + (offset + 1) + '"]')) {
        currentNumber = parseInt(testBlok.getAttribute("id").substring(2));
        if (currentNumber < number)
            continue;
        if (currentNumber >= limit)
            break;
        if (open){
            // recursive !!!
            currentBtn = testBlok.querySelector(".btn-details-all");
            if (all && currentBtn && currentBtn.textContent == "VVV")
                show(currentBtn);
            testBlok.classList.remove("hidden");
        }
        else {
            // for close recursive !!!
            currentBtn = testBlok.querySelector(".btn-details");
            if (currentBtn && currentBtn.textContent == "A"){
                show(currentBtn);
                currentBtn = testBlok.querySelector(".btn-details-all");
                if (currentBtn) // update if have button for all
                    currentBtn.textContent = "VVV"
            }
            testBlok.classList.add("hidden");
        }
    }
};

function expand(srcButton) {
    const parent = srcButton.parentElement;
    const sibling = parent.nextElementSibling;
    const grandpa = parent.parentElement;
    const label = srcButton.firstChild;
    // expand
    if (label.textContent == "+") {
        label.textContent = "-";
        // set height of grandpa if is not set
        if (!grandpa.style.height) {
            var h = grandpa.offsetHeight - 10;
            grandpa.style.height = h + "px";
        }
        sibling.classList.remove("hidden");
        // calculate width of sibling (issue of Chrome)
        if (!sibling.style.width) {
            var listCover = sibling.querySelectorAll(".cover");
            var number = listCover.length;
            var h = listCover[0].offsetHeight;
            var ph = parent.offsetHeight - 10;
            var col = Math.ceil(number / Math.floor(ph / h));
            var width = col;
            for (var i = 0; i < col; i++)
                width += listCover[i * col].offsetWidth;
            sibling.style.width = width + "px";
        }
        // hidden
    } else {
        label.textContent = "+";
        sibling.classList.add("hidden");
    }
};
