// See LICENSE for license details.

package example.test

import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}
import example.FullAdder

class FullAdderUnitTester(c: FullAdder) extends PeekPokeTester(c) {

  private val fa = c

  for(cin <- 0 to 1 by 1) {
    for (in1 <- 0 to 1 by 1) {
      for (in0 <- 0 to 1 by 1) {
        poke(fa.io.cin, cin)
        poke(fa.io.in1, in1)
        poke(fa.io.in0, in0)
        expect(fa.io.cout, ((in0&in1)|(cin&in0)|(cin&in1)))
        expect(fa.io.sout, (in0^in1))
      }
    }
  }
}

class FullAdderTester extends ChiselFlatSpec {
  private val backendNames = Array[String]("firrtl"/*, "verilator"*/)
  for ( backendName <- backendNames ) {
    "FullAdder" should s"Simple 1 bit fulladder imp (with $backendName)" in {
      Driver(() => new FullAdder, backendName) {
        c => new FullAdderUnitTester(c)
      } should be (true)
    }
  }
}
