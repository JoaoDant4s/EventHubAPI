const ticketForm = document.getElementById("ticketForm");

ticketForm.addEventListener("load", ()=>{
    const eventSelect = document.getElementById("event");
    eventSelect.addEventListener("change", ()=>{
        
        const subEvent = document.getElementById(`event-${eventSelect.value}`)
        
        eventSelect.children.array.forEach(e => {
            e.classList.remove("showSubEventCheckbox");
        });
    
        subEvent.classList.add("showSubEventCheckbox");
    })
});