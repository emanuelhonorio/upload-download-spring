class MyFile {
    constructor() {
        this.name = document.getElementById("fileName");
        this.downloadUri = document.getElementById("downloadUri");
        this.type = document.getElementById("fileType");
        this.size = document.getElementById("fileSize");
        this.img = document.getElementById("fileImg");
    }
    
    init() {
        document.getElementById("upload").addEventListener('click', this.upload.bind(this));
    }
    
    upload() {
    	let formData = new FormData();
    	formData.append("file", document.querySelector('[name="file"]').files[0]);
    	console.log(document.querySelector('[name="file"]').files[0]);
    	console.log(formData);
        $.ajax({
            type: 'POST',
            enctype: 'multipart/form-data',
            url: "/files/uploadFile",
            data: formData,
            processData: false,
            contentType: false,
            success: this.onUploadCompleto.bind(this),
            error: function(error) {
                console.log(error);
            }
        });
    }
    
    onUploadCompleto(response) {
    	console.log(response);
        this.name.innerHTML = response.name;
        this.downloadUri.href = response.downloadUri;
        this.downloadUri.innerHTML = response.downloadUri;
        this.type.innerHTML = response.fileType;
        this.size.innerHTML = response.size + ' Bytes';
        this.img.src = response.downloadUri;
    }
}

let file = new MyFile();
file.init();