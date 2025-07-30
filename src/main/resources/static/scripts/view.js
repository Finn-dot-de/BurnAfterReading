document.getElementById("confirmBtn").addEventListener("click", async () => {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get("id");

    if (!id) {
        document.getElementById("notFound").classList.remove("hidden");
        return;
    }

    const res = await fetch(`/api/v1/notes/${id}`, { method: "DELETE" });

    if (res.ok) {
        document.getElementById("readMessage").textContent = await res.text();
        document.getElementById("messageContainer").classList.remove("hidden");
    } else {
        document.getElementById("notFound").classList.remove("hidden");
    }
});
