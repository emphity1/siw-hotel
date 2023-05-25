// Aggiungi un effetto di transizione al click del pulsante di prenotazione
$(document).ready(function() {
    $(".btn-primary").click(function() {
        $(this).addClass("btn-animate");
    });
});
