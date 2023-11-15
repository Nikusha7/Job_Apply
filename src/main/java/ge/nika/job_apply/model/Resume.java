/*
This class gets Part as attribute. Part filePart contains everything about uploaded file, so we are getting name, extension and size from it
Then we are setting values to our attributes (name, directoryPath, extension and size).
We have validation methods for these attributes, they are invoked in UserInputServlet.
 */

package ge.nika.job_apply.model;

import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Resume {
    private Part filePart;

    //Resume attributes(meta-data of resume/cv);
    private int resumeId;
    private String resumeFileName;
    private String resumeFileDirectoryPath;
    private String resumeFileExtension;
    private double resumeFileSize;

    public Resume() {
    }

    public Resume(Part filePart) {
        this.filePart = filePart;
        this.resumeFileDirectoryPath = "D:\\IntelliJ Projects\\Job_Apply\\src\\main\\files"; //it is just a demonstration path
//        it will generate new unique name if file already exists with same name
        this.resumeFileName = generateUniqueResumeName(getFileName(), resumeFileDirectoryPath);
        this.resumeFileExtension = resumeFileName.substring(resumeFileName.lastIndexOf(".")).toLowerCase();
        this.resumeFileSize = filePart.getSize();
    }

    /**
     * Generates a unique filename by appending a counter to the base name if the file already exists in the directory.
     *
     * @param originalFileName The original filename.
     * @param directory        The target directory where the file will be saved.
     * @return A unique filename.
     */
    private String generateUniqueResumeName(String originalFileName, String directory) {

        // Extract the base name and extension from the original filename
        String baseName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        int counter = 0;
        String uniqueResumeName = originalFileName;

//      Check if a file with the same name already exists in the directory. if it does then it generates new name
        while (new File(directory, uniqueResumeName).exists()) {
            counter++;
            uniqueResumeName = baseName + "_" + counter + extension;
        }
        return uniqueResumeName;
    }

    //file name also contains extension of file
    public String getFileName() {
        String filePartHeader = filePart.getHeader("content-disposition");
        for (String content : filePartHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public boolean isValidFileNameSize() {
        return resumeFileName.length() <= 65;
    }

    //checks if file type is valid
    public boolean isValidFileType() {
        Set<String> validFileExtensions = new HashSet<>(Set.of(
                ".pdf", ".doc", ".docx", ".txt", ".rtf", ".odt", ".html",
                ".jpeg", ".jpg", ".png", ".md", ".tex"
        ));

        String fileExtension = resumeFileExtension.toLowerCase(); // Ensuring case-insensitive comparison
        return validFileExtensions.contains(fileExtension);
    }

    //checks if file size is valid
    public boolean isValidFileSize() {
        return filePart.getSize() <= 10 * 1024 * 1024;
    }


    /**
     * Saves the uploaded resume file to the specified directory.
     */
    public void saveResumeFile() {

//      Create directory if it does not exist
        Path uploadDirectory = Paths.get(resumeFileDirectoryPath);

        try {
//          Ensure that the directory and its parent directories exist
            Files.createDirectories(uploadDirectory);

//          Save the uploaded file to the server
            InputStream fileContent = filePart.getInputStream();

//          Create a File object with the unique filename in the target directory
            Path outputFile = Paths.get(resumeFileDirectoryPath, resumeFileName);

            try (FileOutputStream outputStream = new FileOutputStream(outputFile.toFile())) {
//              Copy file content to the output stream using a buffer
                int read;
                final byte[] buffer = new byte[1024];
                while ((read = fileContent.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getResumeId() {
        return resumeId;
    }

    public String getResumeFileName() {
        return resumeFileName;
    }

    public String getResumeFileDirectoryPath() {
        return resumeFileDirectoryPath;
    }

    public String getResumeFileExtension() {
        return resumeFileExtension;
    }

    public double getResumeFileSize() {
        return resumeFileSize;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    public void setResumeFileName(String resumeFileName) {
        this.resumeFileName = resumeFileName;
    }

    public void setResumeFileDirectoryPath(String resumeFileDirectoryPath) {
        this.resumeFileDirectoryPath = resumeFileDirectoryPath;
    }

    public void setResumeFileExtension(String resumeFileExtension) {
        this.resumeFileExtension = resumeFileExtension;
    }

    public void setResumeFileSize(double resumeFileSize) {
        this.resumeFileSize = resumeFileSize;
    }

    @Override
    public String toString() {
        return ", Resume ID='" + resumeId + '\'' +
                ", Resume file name='" + resumeFileName + '\'' +
                ", Resume file directory path='" + resumeFileDirectoryPath + '\'' +
                ", Resume file extension='" + resumeFileExtension + '\'' +
                ", Resume file size=" + resumeFileSize +
                '}';
    }

}
