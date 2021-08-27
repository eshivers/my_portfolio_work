$( "#my_form" ).submit(makeApiCall);

function makeApiCall(event){
    let count = $( "select.form-select" ).first().val()
    if(["30","50","70"].includes(count)){
        count = parseInt(count);
    } else {
        count = 30;
    }
    let tags = $( "input[name$='tags']" ).first().val()
    var url = null;
    if(tags != null){
       url =`https://www.flickr.com/services/rest/?method=flickr.photos.search&\
            api_key=4d6bb66b0b17825cc1cd532686faa82a&tags=${tags}&per_page=${count}&\
            format=json&nojsoncallback=1&extras=url_sq&privacy_filter=1&safe_search=1&page=1`;
    } else {
        url =`https://www.flickr.com/services/rest/?method=flickr.photos.search&\
            api_key=4d6bb66b0b17825cc1cd532686faa82a&per_page=${count}&\
            format=json&nojsoncallback=1&extras=url_sq&privacy_filter=1&safe_search=1&page=1`;
    }
    $.ajax({url:url, dataType:"json"}).then(function(data) {
        div_element = $('#flickr-images');
        output = '<div class="card-columns">';
        data.photos.photo.forEach(element => {
            output += '<div class="card" style="width: 18rem;">';
            output += `<img src="${element.url_sq}" class="card-img-top" alt="${element.title}"></img>`
            output += `<div class="card-body"><h5 class="card-title">${element.title}</h5></div>`
            output += '</div>'
        });
        output += '</div>'
        div_element.empty();
        div_element.append(output);
    });
    event.preventDefault();
}