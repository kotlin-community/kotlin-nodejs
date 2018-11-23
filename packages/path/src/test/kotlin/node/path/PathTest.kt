package node.path

import ava.Suite
import ava.test
import kotlin.test.Test

class PathTest : Suite {
    @Test
    override fun run() {
        test("should provide bindings for standard node path function") {
            it.truthy(Path.delimiter)
            it.truthy(Path.separator)
            val a = Path.join("a/b", "c.d")
            val b = Path.join(Path("a/b"), Path("c.d"))
            it.equal(a.toString(), "a/b/c.d")
            it.equal(b.toString(), "a/b/c.d")
            val c = Path.resolve("a/b", "c.d")
            val d = Path.resolve(Path("a/b"), Path("c.d"))
            it.isTrue(c.toString().endsWith("a/b/c.d"))
            it.isTrue(c.isAbsolute)
            it.isTrue(d.toString().endsWith("a/b/c.d"))
            it.isTrue(d.isAbsolute)
            val e = Path.relative("a/b", "c/d/e.f")
            val f = Path.relative(Path("a/b"), Path("c/d/e.f"))
            it.equal(e.toString(), "../../c/d/e.f")
            it.equal(f.toString(), "../../c/d/e.f")
        }

        test("should create a Path instance") {
            it.equal(Path("a/b/c").toString(), "a/b/c")
            it.equal(Path("/a/b/c.d").toString(), "/a/b/c.d")
            it.equal(Path(Path("a/b")).toString(), "a/b")
            it.equal(Path("a", "b", "c.d").toString(), "a/b/c.d")
            it.equal(Path(Path("a/b"), Path("c.d")).toString(), "a/b/c.d")
        }

        test("should provide directory, filename, extension and absolute flag") {
            val a = Path("a/b/c")
            it.equal(a.directory, "a/b")
            it.equal(a.filename, "c")
            it.equal(a.extension, "")
            it.isFalse(a.isAbsolute)
            val b = Path("/a/b/c.d")
            it.equal(b.directory, "/a/b")
            it.equal(b.filename, "c.d")
            it.equal(b.extension, ".d")
            it.isTrue(b.isAbsolute)
        }

        test("should append path segements") {
            val path = Path()
            path.append("a/b").append(Path("c.d"))
            it.equal(path.toString(), "a/b/c.d")
        }

        test("should prepend path segements") {
            val path = Path("e.f")
            path.prepend("c/d").prepend(Path("a/b"))
            it.equal(path.toString(), "a/b/c/d/e.f")
        }

        test("should resolve relative to absolute path") {
            val path = Path("a/b/c.d")
            path.toAbsolute()
            it.isTrue(path.isAbsolute)
        }

        test("should resolve relative path to target") {
            val path1 = Path("a/b/c/d.e")
            path1.relativeTo("a/b/f/g")
            it.equal(path1.toString(), "../../c/d.e")
            val path2 = Path("a/b/c/d.e")
            path2.relativeTo(Path("a/b/f/g"))
            it.equal(path2.toString(), "../../c/d.e")
        }

        test("should remove path segment") {
            it.equal(
                    Path("a/b/c/a/b/d.e")
                            .remove("a/b", Path.Bias.Start)
                            .toString(),
                    "c/a/b/d.e"
            )
            it.equal(
                    Path("a/b/c/a/b/d.e")
                            .remove("a/b", Path.Bias.End)
                            .toString(),
                    "a/b/c/d.e"
            )
            it.equal(
                    Path("a/b/c/a/b/d.e")
                            .remove("a/b", Path.Bias.All)
                            .toString(),
                    "c/d.e"
            )
            it.equal(
                    Path("a/b/c/a/b/d.e")
                            .remove(Path("a/b"), Path.Bias.Start)
                            .toString(),
                    "c/a/b/d.e"
            )
            it.equal(
                    Path("a/b/c/a/b/d.e")
                            .remove(Path("a/b"), Path.Bias.End)
                            .toString(),
                    "a/b/c/d.e"
            )
            it.equal(
                    Path("a/b/c/a/b/d.e")
                            .remove(Path("a/b"), Path.Bias.All)
                            .toString(),
                    "c/d.e"
            )
        }
    }
}