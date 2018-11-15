@file:JsModule("path")

package Node.Path.Internal

external interface ParsedPath {
    var root: String
    var dir: String
    var base: String
    var ext: String
    var name: String
}
external interface FormatInputPathObject {
    var root: String?
    var dir: String?
    var base: String?
    var ext: String?
    var name: String?
}
external fun normalize(p: String): String = definedExternally
external fun join(vararg paths: String): String = definedExternally
external fun resolve(vararg pathSegments: String): String = definedExternally
external fun isAbsolute(path: String): Boolean = definedExternally
external fun relative(from: String, to: String): String = definedExternally
external fun dirname(p: String): String = definedExternally
external fun basename(p: String, ext: String? = definedExternally /* null */): String = definedExternally
external fun extname(p: String): String = definedExternally
external var sep: String /* String /* "\" */ | String /* "/" */ */ = definedExternally
external var delimiter: String /* String /* ";" */ | String /* ":" */ */ = definedExternally
external fun parse(pathString: String): ParsedPath = definedExternally
external fun format(pathObject: FormatInputPathObject): String = definedExternally

