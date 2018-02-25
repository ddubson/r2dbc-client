/*
 * Copyright 2017-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nebhale.r2dbc.postgresql.codec;

import com.nebhale.r2dbc.postgresql.client.Parameter;
import com.nebhale.r2dbc.postgresql.message.Format;
import com.nebhale.r2dbc.postgresql.type.PostgresqlObjectId;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static java.util.Objects.requireNonNull;

final class ByteCodec extends AbstractCodec<Byte> {

    private final ShortCodec delegate;

    ByteCodec(ByteBufAllocator byteBufAllocator) {
        super(Byte.class);
        this.delegate = new ShortCodec(byteBufAllocator);
    }

    @Override
    public Byte decode(ByteBuf byteBuf, Format format, Class<? extends Byte> type) {
        return this.delegate.decode(byteBuf, format, Short.class).byteValue();
    }

    @Override
    public Parameter doEncode(Byte value) {
        requireNonNull(value, "value must not be null");

        return this.delegate.doEncode((short) value);
    }

    @Override
    boolean doCanDecode(Format format, PostgresqlObjectId type) {
        return this.delegate.doCanDecode(format, type);
    }

}