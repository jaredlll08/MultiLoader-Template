export interface Config {
    lineReplacements: {
        [name: string]: LineReplacement[]
    }
}

export interface Cache {
    mcVersion: string;
    forgeVersions: { promos: { [key: string]: string } };
    fabricLoaderVersions: { loader: { version: string } }[]
    fabricVersion: string
}

export interface LineReplacement {
    regex: string | RegExp,
    replacement: (old: string, matches: RegExpMatchArray, cache: Cache) => string
}