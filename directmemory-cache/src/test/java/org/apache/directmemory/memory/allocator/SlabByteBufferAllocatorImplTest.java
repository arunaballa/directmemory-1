package org.apache.directmemory.memory.allocator;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class SlabByteBufferAllocatorImplTest
{
    @Test
    public void allocationTest()
    {
        
        List<FixedSizeByteBufferAllocatorImpl> slabs = new ArrayList<FixedSizeByteBufferAllocatorImpl>();
        slabs.add( new FixedSizeByteBufferAllocatorImpl( 0, 1024, 128, 1 ) );
        slabs.add( new FixedSizeByteBufferAllocatorImpl( 1, 1024, 256, 1 ) );
        slabs.add( new FixedSizeByteBufferAllocatorImpl( 2, 1024, 512, 1 ) );
        slabs.add( new FixedSizeByteBufferAllocatorImpl( 3, 1024, 1024, 1 ) );
        
        
        ByteBufferAllocator allocator = new SlabByteBufferAllocatorImpl( 0, slabs, false );
        
        ByteBuffer bf1 = allocator.allocate( 250 );
        Assert.assertEquals( 256, bf1.capacity() );
        Assert.assertEquals( 250, bf1.limit() );
        
        ByteBuffer bf2 = allocator.allocate( 251 );
        Assert.assertEquals( 256, bf2.capacity() );
        Assert.assertEquals( 251, bf2.limit() );
        
        ByteBuffer bf3 = allocator.allocate( 200 );
        Assert.assertEquals( 256, bf3.capacity() );
        Assert.assertEquals( 200, bf3.limit() );
        
        ByteBuffer bf4 = allocator.allocate( 100 );
        Assert.assertEquals( 128, bf4.capacity() );
        Assert.assertEquals( 100, bf4.limit() );
        
        ByteBuffer bf5 = allocator.allocate( 550 );
        Assert.assertEquals( 1024, bf5.capacity() );
        Assert.assertEquals( 550, bf5.limit() );
        
        ByteBuffer bf6 = allocator.allocate( 800 );
        Assert.assertNull( bf6 );

        allocator.free( bf5 );
        
        ByteBuffer bf7 = allocator.allocate( 800 );
        Assert.assertEquals( 1024, bf7.capacity() );
        Assert.assertEquals( 800, bf7.limit() );
        
    }
    
}
