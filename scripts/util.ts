import fs from "fs";
import path from "path";

export const filterHiddenFiles = (file: string) => {
    return !file.startsWith(".");
}

export const filterIgnoredFiles = (file: string) => {

    return filterHiddenFiles(file) && !(file === "node_modules" || file === "dist" || file === "scripts");
}

export const collectFiles = (filePath: string, foundFiles: string[]) => {
    if (fs.lstatSync(filePath).isDirectory()) {
        fs.readdirSync(filePath).filter(filterHiddenFiles).forEach(subFilePath => collectFiles(path.join(filePath, subFilePath), foundFiles))
    } else {
        foundFiles.push(filePath);
    }
}