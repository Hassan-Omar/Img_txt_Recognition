# Image Text Recognition
this is a repo for java project use Google API to get Text from image (png , jpg ,...)

# Steps
 - Download OCR  you can click    [this](https://chillyfacts.com/wp-content/uploads/2017/05/tesseract-ocr-setup-3.02.02.rar) to download the file
 - install OCR
 just run insraller and click next but you must sure that installtion location is  C:\Program Files (x86) 
 - clone my project only type on bash 
 ```git 
    git clone https://github.com/Hassan-Omar/Img_txt_Recognition
 ```
 - Define the folders needed 
 create in Documents folder TR_LTA then create tow folders IN , OUT 
 - Run and Test now you need to put your image into IN folder then run the project and you will git text file in OUT folder  
 
 ---
 ## Input & Output 
 
 input 
 ![1](https://user-images.githubusercontent.com/49106965/60827289-b8c17b00-a164-11e9-97af-74ef89f8fd29.png)

 output
 ![out](https://user-images.githubusercontent.com/49106965/60827544-4d2bdd80-a165-11e9-91d7-63edc4b3b7e9.JPG)

 ---
 ## Problems
  you can meet exceptions (IOExceptions) to fix this you need to open the source code and edite input path , install path , output path 
  
  ```java 
  String input_file = "C:\\Program Files (x86)\\Tesseract-OCR\\"+imageName;
  String output_file = "C:\\Users\\h.omar\\Documents\\TR_LTA\\OUT\\1";
  String tesseract_install_path = "C:\\Program Files (x86)\\Tesseract-OCR\\tesseract";
  ```
this created by **Hassan Omar** 
