package Node.Path

data class ParsedPath(
        var root: String,
        var directory: String,
        var filename: String,
        var extension: String,
        var name: String
)