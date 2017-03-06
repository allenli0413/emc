/**
 * All rights reserved.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.litesuits.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 * Title:加密处理类 
 * Description: 字符串的加密处理 
 * </pre>
 * @author zwyl
 * @since 2016年6月23日
 * @version 1.0
 */
public class EncryptionUtils {

	/**
	 * Description: 将指定的字符串做md5加密，并返回加密后的字符串。 
	 * @param context  指定的字符串 
	 * @return 加密后的字符串 
	 * @author zwyl
	 * @since 2016年6月23日 上午11:28:09
	 */
	public static String md5Encode(String context) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(context.getBytes());
			byte[] encryContext = md.digest();

			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < encryContext.length; offset++) {
				i = encryContext[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return context;
		}
	}
}
