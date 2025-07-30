async function sendMessage() {
    const content = document.getElementById("message").value.trim();
    if (!content) {
        alert("Nachricht darf nicht leer sein.");
        return;
    }

    const res = await fetch("/api/v1/notes", {
        method: "POST",
        headers: { "Content-Type": "text/plain" },
        body: content
    });

    if (res.ok) {
        const id = await res.text();
        document.getElementById("link").value = `${location.origin}/view.html?id=${id}`;
        document.getElementById("linkContainer").classList.remove("hidden");
    } else {
        alert("Fehler beim Speichern.");
    }
}

function copyLink() {
    const linkInput = document.getElementById("link");
    const copyButton = event.target; // der Button, der geklickt wurde

    navigator.clipboard.writeText(linkInput.value)
        .then(() => {
            const originalText = copyButton.textContent;
            copyButton.textContent = "✔️ Kopiert!";
            copyButton.disabled = true;

            setTimeout(() => {
                copyButton.textContent = originalText;
                copyButton.disabled = false;
            }, 2000);
        })
        .catch(err => {
            console.error("Fehler beim Kopieren:", err);
            copyButton.textContent = "Fehler!";
            setTimeout(() => {
                copyButton.textContent = "Link kopieren";
            }, 2000);
        });
}

const textarea = document.getElementById("message");

textarea.addEventListener("input", () => {
    textarea.style.height = "auto"; // Zurücksetzen
    textarea.style.height = textarea.scrollHeight + "px"; // Höhe an Inhalt anpassen
});

