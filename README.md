# MultiLoader templates

This branch serves as a central repository of MultiLoader templates and examples.

to use a template, simply

```
git clone https://github.com/jaredlll08/MultiLoader-Template.git
```

or download the code as a ZIP file, copy the template you want to use and rename it for your project.

# Adding new templates

To add a new template, simply create a folder for it, add the contents and then make a PR!

If your new template has any version numbers that can be updated (like the Forge version for example), then please make sure that there is a replacement for it (if possible) in the `scripts/config.ts` file.

# Scripts

This branch also contains house-keeping scripts to ensure that the templates are (mostly) up to date.

They are written in TypeScript and can be found in the `scripts` directory.

To update the templates, simply run:

```
npm run update
```

which will go through all the replacements in `scripts/config.ts` and update their values.

Note, this currently only works on Windows due to file paths.
Note, Unfortunately not every version format is supported, notably the versions in the `quilt.mod.json` file in the Quilt template as it spans over multiple lines. 
