if ( $.fn.makisu.enabled ) {
    var $maki = $( '.maki' );

    $maki.makisu({
        selector: 'dd',
        overlap: 0.6,
        speed: 0.85
    });

    $( '.list' ).makisu( 'open' );

    // Toggle on click

    $( '.toggle' ).on( 'click', function() {
        $( '.list' ).makisu( 'toggle' );
    });

    // Disable all links

    $( '.demo a' ).click( function( event ) {
        event.preventDefault();
    });

} else {

    $( '.warning' ).show();
}