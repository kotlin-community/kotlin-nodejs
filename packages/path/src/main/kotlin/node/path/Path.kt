package node.path

import node.path.basename as _basename
import node.path.delimiter as _delimiter
import node.path.dirname as _dirname
import node.path.extname as _extname
import node.path.format as _format
import node.path.isAbsolute as _isAbsolute
import node.path.join as _join
import node.path.normalize as _normalize
import node.path.parse as _parse
import node.path.relative as _relative
import node.path.resolve as _resolve
import node.path.sep as _sep

class Path(value: String = "") {
    enum class Bias {
        Start,
        End,
        All
    }

    companion object {
        val separator: String
            get() = _sep

        val delimiter: String
            get() = _delimiter

        fun join(vararg segments: String): Path = Path(_join(*segments))

        fun join(vararg segments: Path): Path {
            return Path(_join(*segments.map { it.toString() }.toTypedArray()))
        }

        fun resolve(vararg segments: String): Path = Path(_resolve(*segments))

        fun resolve(vararg segments: Path): Path {
            return Path(_resolve(*segments.map { it.toString() }.toTypedArray()))
        }

        fun relative(from: String, to: String): Path = Path(_relative(from, to))

        fun relative(from: Path, to: Path): Path = Path(_relative(from.toString(), to.toString()))
    }

    var directory: String = ""
        set(value) {
            field = _normalize(value)
        }
    var filename: String = ""
        set(value) {
            field = _normalize(value)
        }
    val extension: String
        get() = _extname(this.filename)
    val isAbsolute: Boolean
        get() = _isAbsolute(this.directory)

    init {
        val parsedPath = _parse(value)
        this.directory = _join(parsedPath.root, parsedPath.dir)
        this.filename = parsedPath.base
    }

    constructor(vararg segments: String) : this(_join(*segments))

    constructor(vararg segments: Path) : this(_join(*segments.map { it.toString() }.toTypedArray()))

    constructor(path: Path) : this(path.toString())

    fun append(vararg segments: String): Path {
        val path = Path(_join(this.toString(), *segments))
        this.directory = path.directory
        this.filename = path.filename
        return this
    }

    fun append(vararg segments: Path): Path {
        return this.append(*segments.map { it.toString() }.toTypedArray())
    }

    fun prepend(vararg segments: String): Path {
        val path = Path(_join(*segments, this.toString()))
        this.directory = path.directory
        this.filename = path.filename
        return this
    }

    fun prepend(vararg segments: Path): Path {
        return this.prepend(*segments.map { it.toString() }.toTypedArray())
    }

    private fun <S, T> findSequentialIntersection(sourceSegments: List<out S>, targetSegments: List<out T>): IntRange? {
        var targetIndex = 0
        var startIndex: Int? = null
        var endIndex: Int? = null
        var found = false

        for ((index, sourceSegment) in sourceSegments.withIndex()) {
            if (targetIndex < targetSegments.size) {
                if (sourceSegment == targetSegments[targetIndex] && startIndex == null && !found) {
                    startIndex = index
                    targetIndex++
                    found = true
                } else if (sourceSegment == targetSegments[targetIndex] && startIndex != null && found) {
                    targetIndex++
                } else if (sourceSegment != targetSegments[targetIndex] && endIndex == null && found) {
                    found = false
                    targetIndex++
                }
            } else if (endIndex == null) {
                endIndex = index
            }
        }

        if (startIndex == null || endIndex == null) {
            return null
        }

        return IntRange(startIndex, endIndex)
    }

    private fun <T> filterOutRange(target: List<T>, range: IntRange): List<T> {
        val results = ArrayList<T>()
        for ((index, value) in target.withIndex()) {
            if (index < range.start) {
                results.add(value)
            } else if (index >= range.endInclusive) {
                results.add(value)
            }
        }
        return results.toList()
    }

    fun remove(target: String, bias: Path.Bias = Path.Bias.All): Path {
        val targetSegments = target.split(Path.separator)
        val sourceSegments = this.toString().split(Path.separator)

        val outputSegments = when (bias) {
            Path.Bias.Start -> {
                val range = this.findSequentialIntersection(sourceSegments, targetSegments)
                if (range != null) this.filterOutRange(sourceSegments, range) else sourceSegments
            }
            Path.Bias.End -> {
                val range = this.findSequentialIntersection(sourceSegments.reversed(), targetSegments.reversed())
                if (range != null) this.filterOutRange(sourceSegments.reversed(), range).reversed() else sourceSegments
            }
            Path.Bias.All -> {
                var output = sourceSegments
                do {
                    val range = this.findSequentialIntersection(output, targetSegments)
                    if (range != null) {
                        output = this.filterOutRange(output, range)
                    }

                } while (range != null)
                output
            }
        }
        val path = Path(*outputSegments.toTypedArray())
        this.directory = path.directory
        this.filename = path.filename
        return this
    }

    fun remove(target: Path, bias: Path.Bias = Path.Bias.All): Path {
        this.remove(target.toString(), bias)
        return this
    }

    fun toAbsolute(): Path {
        this.directory = _resolve(this.directory)
        return this
    }

    fun relativeTo(target: String): Path {
        val relativePath = Path(_relative(target, this.toString()))
        this.directory = relativePath.directory
        this.filename = relativePath.filename
        return this
    }


    fun relativeTo(target: Path): Path {
        return this.relativeTo(target.toString())
    }

    /**
     * Get a node compatible string a path
     */
    override fun toString(): String {
        return _join(this.directory, this.filename)
    }

    /**
     * Returns directory component
     */
    operator fun component1(): String {
        return this.directory
    }

    /**
     * Returns filename component
     */
    operator fun component2(): String {
        return this.filename
    }

    /**
     * Returns extension component
     */
    operator fun component3(): String {
        return this.extension
    }

    /**
     * Returns boolean indicating if path is absolute
     */
    operator fun component4(): Boolean {
        return this.isAbsolute
    }

}