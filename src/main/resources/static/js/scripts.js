const etfsToCompare = ["", ""];
let indexToInsertIsin = 0;
const etfList = document.getElementById("etf-list");
const compareFab = document.getElementById("compare-etf-button");

document.addEventListener('DOMContentLoaded', function() {
    // var elems = document.querySelectorAll('.sidenav');
    // var instances = M.Sidenav.init(elems);
    M.AutoInit(document.body);
    addOnClickListenersToEtfEntries();
    addOnClickListenerToCompareButton();
});

function addOnClickListenersToEtfEntries() {
    for (let i = 0; i < etfList.children.length; i++) {
        etfList.children.item(i).addEventListener("click", (event) => toggleCompareState(event, i));
    }
}

function toggleCompareState(event, elementIndex) {
    let element = etfList.children.item(elementIndex);
    let etfIsin = element.children.item(0).children.item(1).children.item(0).children.item(0).textContent;
    let indexOfElement = etfsToCompare.indexOf(etfIsin);

    if (indexOfElement !== -1) {
        element.classList.remove(getHighlightCssClassBasedOnIndex(indexOfElement), "lighten-3", "indigo-text", "text-lighten-1");
        etfsToCompare[indexOfElement] = "";
        indexToInsertIsin = indexOfElement;
        togglePulseEffect();
    } else {
        if (positionIsOccupied(0) && positionIsOccupied(1)) {
            return;
        } else if (indexToInsertIsin === 1 && !positionIsOccupied(0)) {
            indexToInsertIsin = 0;
        }
        etfsToCompare[indexToInsertIsin] = etfIsin;
        element.classList.add(getHighlightCssClassBasedOnIndex(indexToInsertIsin), "lighten-3", "indigo-text", "text-lighten-1");
        // invert so if index was 0, ist is 1, or vice versa
        indexToInsertIsin = indexToInsertIsin ^ 1;
        togglePulseEffect();
    }
}

function getHighlightCssClassBasedOnIndex(index) {
    if (index === 0) {
        return "grey";
    } else {
        return "grey";
    }
}

function togglePulseEffect() {
    if (positionIsOccupied(0) && positionIsOccupied(1)) {
        compareFab.classList.remove("grey");
        compareFab.classList.add("pulse", "indigo", "lighten-1");
    } else {
        compareFab.classList.remove("pulse", "indigo", "lighten-1");
        compareFab.classList.add("grey");
    }
}

function positionIsOccupied(position) {
    return etfsToCompare[position].length > 0;
}

function addOnClickListenerToCompareButton() {
    compareFab.addEventListener("click", (event) => compare(event));
}

function compare() {
    if (positionIsOccupied(0) && positionIsOccupied(1)) {
        let etfIsin = etfsToCompare[0];
        let otherEtfIsin = etfsToCompare[1];
        window.location.href="/?etf=" + etfIsin + "&otherEtf=" + otherEtfIsin;
    }
}
